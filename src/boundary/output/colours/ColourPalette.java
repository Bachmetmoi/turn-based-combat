package boundary.output.colours;

import entity.interfaces.Describable;
import entity.interfaces.Named;

public interface ColourPalette extends Named, Describable {
    String reset();
    String bold();

    // Brand/Theme colours
    String primary();
    String secondary();
    String accent();

    // Feedback colours
    String success();
    String danger();
    String warning();
    String info();
    String neutral();

    // Role-based colours
    String player();
    String enemy();
    String boss();

    // Health/Status levels
    String high();
    String medium();
    String low();
    
    // Background/UI elements
    String divider();
    String softDivider();
}
