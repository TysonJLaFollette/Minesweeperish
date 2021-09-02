# Minesweeperish
Minesweeperish is a clone of the classical Minesweeper game. It does not add any new features.
## Tutorial
## How-To
## Understanding
## Technical Reference
## To-do list
* Refactor the project into the Model.Model-View-Presenter design pattern while leaving it in a working state after every commit.
    * Separate the MainGame class into smaller classes.
        * Separate data storage functionality into a Model.Model class.
            * Initialize the new model, then use it to fill the old model, leaving both accessible.
            * One by one, modify functions to use the new model instead of the old model. Leave both models in a working state.
            * Delete the old model once all functions have been migrated.
            * Create a Model.Model Interface, and a class which implements it.
                * Make the functions empty at first, if necessary.
            * Move flags functionality from MainGame into this model class.
            * Move MineLocations functionality from MainGame into Model.Model class.
            * Move Question mark functionality to Model.Model class. 
        * Separate display logic into a separate View class.
            * Move logic for cell icons.
        * Change whatever is left of the MainGame class into a Presenter class.