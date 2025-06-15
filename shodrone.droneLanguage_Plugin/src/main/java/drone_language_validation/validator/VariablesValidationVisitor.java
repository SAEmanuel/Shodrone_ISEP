package drone_language_validation.validator;

import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

import drone_language_validation.DroneGenericPlugin;
import drone_language_validation.DroneGenericPlugin.VariableKind;
import drone_language_validation.RequiredFields;
import drone_language_validation.generated.droneGenericBaseVisitor;
import drone_language_validation.generated.droneGenericParser;
import drone_language_validation.generated.droneGenericParser.VectorContext;
import drone_language_validation.generated.droneGenericParser.ExpressionContext;

public class VariablesValidationVisitor extends droneGenericBaseVisitor<Void> {
    private final DroneGenericPlugin plugin;
    private final Set<String> declaredVars = new HashSet<>();

    public VariablesValidationVisitor(DroneGenericPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Void visitSection_variables(drone_language_validation.generated.droneGenericParser.Section_variablesContext ctx) {
        for (drone_language_validation.generated.droneGenericParser.Variable_declarationContext varCtx : ctx.variable_declaration()) {
            String typeName = varCtx.ID(0).getText();
            String varName  = varCtx.ID(1).getText();
            ExpressionContext exprCtx = varCtx.expression();

            // (1) Tipo deve existir em Types
            if (!RequiredFields.TYPES.contains(typeName)) {
                plugin.addError(
                        String.format(
                                "Invalid type '%s' in variable declaration for '%s'.",
                                typeName, varName
                        )
                );
            }

            // (2) Sem duplicatas e registro
            if (!declaredVars.add(varName)) {
                plugin.addError(
                        String.format("Duplicate variable name '%s'.", varName)
                );
            } else {
                // determina kind e registra
                boolean isScalar = isNumericExpression(exprCtx);
                boolean isArray  = exprCtx.array_literal() != null
                        || (exprCtx.vector() != null
                        && exprCtx.vector().expression().stream()
                        .allMatch(e -> e.vector() != null));
                VariableKind kind = isScalar
                        ? VariableKind.SCALAR
                        : isArray
                        ? VariableKind.ARRAY
                        : VariableKind.VECTOR;
                plugin.registerVariable(varName, typeName, kind);
            }

            // classificação estrutural
            VectorContext vCtx = exprCtx.vector();
            boolean isScalarExpr = isNumericExpression(exprCtx);
            boolean isArray      = exprCtx.array_literal() != null
                    || (vCtx != null
                    && vCtx.expression().stream()
                    .allMatch(c -> c.vector() != null));
            boolean isVectorLit  = vCtx != null
                    && vCtx.expression().size() == 3
                    && vCtx.expression().stream()
                    .allMatch(this::isNumericExpression);
            boolean isVectorOp   = vCtx != null && vCtx.vector().size() == 2;
            List<VectorContext> arrayVectors = new ArrayList<>();
            if (exprCtx.array_literal() != null) {
                arrayVectors.addAll(exprCtx.array_literal().vector());
            } else if (vCtx != null && isArray && vCtx.vector().isEmpty()) {
                for (ExpressionContext comp : vCtx.expression()) {
                    arrayVectors.add(comp.vector());
                }
            }

            // (3) Regras por tipo
            switch (typeName) {
                case "LinearVelocity":
                case "AngularVelocity":
                case "Distance":
                case "Time":
                    if (!isScalarExpr) {
                        plugin.addError(
                                String.format(
                                        "Invalid literal for type '%s'. Expected numeric expression but found '%s'.",
                                        typeName,
                                        exprCtx.getText()
                                )
                        );
                    }
                    break;
                case "Point":
                case "Vector":
                    if (!(isVectorLit || isArray || isVectorOp)) {
                        plugin.addError(
                                String.format(
                                        "Invalid literal for type '%s'. Expected vector literal, array of vectors, or vector operation.",
                                        typeName
                                )
                        );
                    }
                    break;
            }

            // (4) Validações finais
            if (isVectorLit) {
                validateVectorComponents(vCtx);
            }
            if (isArray) {
                for (VectorContext vc : arrayVectors) {
                    validateVectorComponents(vc);
                }
            }
            if (isVectorOp) {
                validateVectorOperation(vCtx);
            }
        }
        return null;
    }

    private void validateVectorComponents(VectorContext vCtx) {
        int count = vCtx.expression().size();
        if (count != 3) {
            plugin.addError(
                    String.format(
                            "Invalid vector arity: expected 3 components but got %d.",
                            count
                    )
            );
        }
        for (ExpressionContext comp : vCtx.expression()) {
            if (!isNumericExpression(comp)) {
                plugin.addError(
                        String.format(
                                "Invalid vector component '%s'. Expected numeric expression.",
                                comp.getText()
                        )
                );
            }
        }
    }

    private void validateVectorOperation(VectorContext vCtx) {
        VectorContext left  = vCtx.vector(0);
        VectorContext right = vCtx.vector(1);

        if (left.vector().size() == 2) {
            validateVectorOperation(left);
        } else {
            validateVectorComponents(left);
        }
        if (right.vector().size() == 2) {
            validateVectorOperation(right);
        } else {
            validateVectorComponents(right);
        }
    }

    // aceita NUMBER ou PI ou ops entre eles
    private boolean isNumericExpression(ExpressionContext ctx) {
        if (ctx.NUMBER() != null) return true;
        if (ctx.ID()     != null) return "PI".equals(ctx.ID().getText());
        if (ctx.op       != null) {
            return isNumericExpression(ctx.expression(0))
                    && isNumericExpression(ctx.expression(1));
        }
        if (ctx.getChildCount() == 3
                && "(".equals(ctx.getChild(0).getText())
                && ")".equals(ctx.getChild(2).getText())) {
            return isNumericExpression(ctx.expression(0));
        }
        return false;
    }
}
