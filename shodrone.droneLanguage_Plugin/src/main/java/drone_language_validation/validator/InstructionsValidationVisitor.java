package drone_language_validation.validator;

import java.util.Collections;
import java.util.List;

import drone_language_validation.DroneGenericPlugin;
import drone_language_validation.DroneGenericPlugin.VariableKind;
import drone_language_validation.RequiredFields;
import drone_language_validation.generated.droneGenericBaseVisitor;
import drone_language_validation.generated.droneGenericParser.ExpressionContext;
import drone_language_validation.generated.droneGenericParser.InstructionContext;
import drone_language_validation.generated.droneGenericParser.Section_instructionsContext;

public class InstructionsValidationVisitor extends droneGenericBaseVisitor<Void> {
    private final DroneGenericPlugin plugin;

    public InstructionsValidationVisitor(DroneGenericPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Void visitSection_instructions(Section_instructionsContext ctx) {
        for (InstructionContext instr : ctx.instruction()) {
            String name = instr.ID().getText();

            // (1) Instruction name must be valid
            if (!RequiredFields.INSTRUCTIONS.contains(name)) {
                plugin.addError(
                        String.format(
                                "Invalid instruction '%s'. Valid instructions are: %s",
                                name,
                                RequiredFields.INSTRUCTIONS.getFieldNames()
                        )
                );
                continue;
            }

            // (2) Collect parameters
            List<ExpressionContext> params = instr.param_list() != null
                    ? instr.param_list().expression()
                    : Collections.emptyList();

            // (3) Undeclared-variable check (ignore PI)
            for (ExpressionContext p : params) {
                if (p.ID() != null
                        && p.vector() == null
                        && p.array_literal() == null
                        && !"PI".equals(p.ID().getText())) {
                    String varName = p.ID().getText();
                    if (!plugin.isVariableDeclared(varName)) {
                        plugin.addError(
                                String.format(
                                        "Undeclared variable '%s' used in instruction '%s'.",
                                        varName, name
                                )
                        );
                    }
                }
            }

            // (4) Arity & type checks per instruction
            switch (name) {
                case "takeOff":
                    // 2 args: both numeric
                    if (params.size() != 2) {
                        plugin.addError(
                                String.format(
                                        "Instruction 'takeOff' expects 2 arguments but got %d",
                                        params.size()
                                )
                        );
                    } else {
                        if (!isNumericParam(params.get(0))) {
                            plugin.addError(
                                    "Invalid argument 1 for 'takeOff': expected numeric expression"
                            );
                        }
                        if (!isNumericParam(params.get(1))) {
                            plugin.addError(
                                    "Invalid argument 2 for 'takeOff': expected numeric expression"
                            );
                        }
                    }
                    break;

                case "move":
                    // overloaded:
                    //  - move(Point, scalar)
                    //  - move(Vector, scalar, scalar)
                    if (params.size() == 2) {
                        ExpressionContext first = params.get(0);
                        ExpressionContext second = params.get(1);

                        // first must be a declared Point variable
                        if (!(first.ID() != null
                                && plugin.isVariableDeclared(first.ID().getText())
                                && "Point".equals(plugin.getVariableType(first.ID().getText())))) {
                            plugin.addError(
                                    "Invalid argument 1 for 'move': expected declared Point variable"
                            );
                        }
                        if (!isNumericParam(second)) {
                            plugin.addError(
                                    "Invalid argument 2 for 'move': expected numeric expression"
                            );
                        }
                    }
                    else if (params.size() == 3) {
                        ExpressionContext first = params.get(0);
                        ExpressionContext second = params.get(1);
                        ExpressionContext third = params.get(2);

                        // first must be a vector literal OR declared Vector variable
                        boolean firstIsVectorLiteral = first.vector() != null;
                        boolean firstIsVectorVar     = first.ID() != null
                                && plugin.isVariableDeclared(first.ID().getText())
                                && "Vector".equals(plugin.getVariableType(first.ID().getText()));

                        if (!(firstIsVectorLiteral || firstIsVectorVar)) {
                            plugin.addError(
                                    "Invalid argument 1 for 'move': expected vector literal or declared Vector variable"
                            );
                        }
                        if (!isNumericParam(second)) {
                            plugin.addError(
                                    "Invalid argument 2 for 'move': expected numeric expression"
                            );
                        }
                        if (!isNumericParam(third)) {
                            plugin.addError(
                                    "Invalid argument 3 for 'move': expected numeric expression"
                            );
                        }
                    }
                    else {
                        plugin.addError(
                                String.format(
                                        "Instruction 'move' expects 2 (Point) or 3 (Vector) arguments but got %d",
                                        params.size()
                                )
                        );
                    }
                    break;

                case "movePath":
                    // 2 args: vector or array of vectors, and numeric
                    if (params.size() != 2) {
                        plugin.addError(
                                String.format(
                                        "Instruction 'movePath' expects 2 arguments but got %d",
                                        params.size()
                                )
                        );
                    } else {
                        ExpressionContext first = params.get(0);
                        // literal vector or array, or declared ARRAY variable
                        boolean okFirst = first.vector() != null
                                || first.array_literal() != null
                                || (first.ID() != null
                                && plugin.isVariableDeclared(first.ID().getText())
                                && plugin.getVariableKind(first.ID().getText()) == VariableKind.ARRAY);
                        if (!okFirst) {
                            plugin.addError(
                                    "Invalid argument 1 for 'movePath': expected vector literal or array of vectors"
                            );
                        }
                        if (!isNumericParam(params.get(1))) {
                            plugin.addError(
                                    "Invalid argument 2 for 'movePath': expected numeric expression"
                            );
                        }
                    }
                    break;

                case "moveCircle":
                    // 3 args: vector, numeric, numeric
                    if (params.size() != 3) {
                        plugin.addError(
                                String.format(
                                        "Instruction 'moveCircle' expects 3 arguments but got %d",
                                        params.size()
                                )
                        );
                    } else {
                        if (!isVectorParam(params.get(0))) {
                            plugin.addError(
                                    "Invalid argument 1 for 'moveCircle': expected vector"
                            );
                        }
                        if (!isNumericParam(params.get(1))) {
                            plugin.addError(
                                    "Invalid argument 2 for 'moveCircle': expected numeric expression"
                            );
                        }
                        if (!isNumericParam(params.get(2))) {
                            plugin.addError(
                                    "Invalid argument 3 for 'moveCircle': expected numeric expression"
                            );
                        }
                    }
                    break;

                case "hoover":
                    // 1 arg: numeric
                    if (params.size() != 1) {
                        plugin.addError(
                                String.format(
                                        "Instruction 'hoover' expects 1 argument but got %d",
                                        params.size()
                                )
                        );
                    } else if (!isNumericParam(params.get(0))) {
                        plugin.addError(
                                "Invalid argument for 'hoover': expected numeric expression"
                        );
                    }
                    break;

                case "lightsOn":
                    // 3 args: integer literals in [0,255]
                    if (params.size() != 3) {
                        plugin.addError(
                                String.format(
                                        "Instruction 'lightsOn' expects 3 arguments but got %d",
                                        params.size()
                                )
                        );
                    } else {
                        for (int i = 0; i < 3; i++) {
                            ExpressionContext p = params.get(i);
                            String txt = p.getText();
                            if (p.NUMBER() == null) {
                                plugin.addError(
                                        String.format(
                                                "Invalid argument %d for 'lightsOn': expected integer literal",
                                                i + 1
                                        )
                                );
                            } else {
                                try {
                                    int v = Integer.parseInt(txt);
                                    if (v < 0 || v > 255) {
                                        plugin.addError(
                                                String.format(
                                                        "Invalid argument %d for 'lightsOn': value %d out of range [0,255]",
                                                        i + 1, v
                                                )
                                        );
                                    }
                                } catch (NumberFormatException ex) {
                                    plugin.addError(
                                            String.format(
                                                    "Invalid argument %d for 'lightsOn': not a valid integer",
                                                    i + 1
                                            )
                                    );
                                }
                            }
                        }
                    }
                    break;

                case "lightsOff":
                    // no args
                    if (!params.isEmpty()) {
                        plugin.addError(
                                String.format(
                                        "Instruction 'lightsOff' expects no arguments but got %d",
                                        params.size()
                                )
                        );
                    }
                    break;

                case "land":
                    // 1 arg: numeric
                    if (params.size() != 1) {
                        plugin.addError(
                                String.format(
                                        "Instruction 'land' expects 1 argument but got %d",
                                        params.size()
                                )
                        );
                    } else if (!isNumericParam(params.get(0))) {
                        plugin.addError(
                                "Invalid argument for 'land': expected numeric expression"
                        );
                    }
                    break;
            }
        }

        return super.visitSection_instructions(ctx);
    }

    /** true if NUMBER, PI or declared SCALAR variable or any numeric expression */
    private boolean isNumericParam(ExpressionContext ctx) {
        // vectors/arrays are not numeric here
        if (ctx.vector() != null || ctx.array_literal() != null) return false;

        if (ctx.ID() != null) {
            String id = ctx.ID().getText();
            if ("PI".equals(id)) return true;
            return plugin.isVariableDeclared(id)
                    && plugin.getVariableKind(id) == VariableKind.SCALAR;
        }
        // otherwise NUMBER or arithmetic expression
        return true;
    }

    /** true if vector literal or declared VECTOR (Point or Vector) variable */
    private boolean isVectorParam(ExpressionContext ctx) {
        if (ctx.vector() != null) return true;
        if (ctx.ID() != null) {
            String id = ctx.ID().getText();
            return plugin.isVariableDeclared(id)
                    && plugin.getVariableKind(id) == VariableKind.VECTOR;
        }
        return false;
    }

    /** true if array literal or declared ARRAY variable */
    private boolean isArrayParam(ExpressionContext ctx) {
        if (ctx.array_literal() != null) return true;
        if (ctx.ID() != null) {
            String id = ctx.ID().getText();
            return plugin.isVariableDeclared(id)
                    && plugin.getVariableKind(id) == VariableKind.ARRAY;
        }
        return false;
    }
}
