## Semantic Validation Considerations

To ensure correct use of the DSL, the following checks must be implemented beyond syntax parsing:

1. **RGB Color Limits**  
   - Validate that RGB values (r, g, b) are integers between 0 and 255.

2. **Variable Existence**  
   - Every `ID` used (as parameters or expressions) must have been declared previously (e.g., Position, Velocity, etc.).

3. **Type Coherence**  
   - Check that variables or expressions used as vectors, numbers, etc., match the expected type.  
     Example: `.move` expects the first parameter to be a vector.

4. **Value Constraints**  
   - Validate that parameters like distance, speed, pause duration are positive and within plausible ranges.

5. **Element Exclusivity**  
   - Figures must not mix 3D bitmaps (if implemented in future) with primitive shapes.

6. **Block Order**  
   - Ensure blocks appear in a logical order: `before` → `group` → `pause` → `after`.

7. **Group Block Consistency**  
   - If `group` is used with `{}`, it must contain at least two instructions; otherwise, it should be omitted.

8. **Mathematical Expression Evaluation**  
   - Prevent division by zero and other invalid operations in expressions.

9. **DSL Version Support**  
   - Confirm that the declared DSL version is supported by the system.

10. **Reserved Names**  
    - Prevent use of reserved DSL keywords as variable or element names.

These checks ensure that the DSL description is syntactically valid, semantically coherent, and safe to execute in real drone shows.

