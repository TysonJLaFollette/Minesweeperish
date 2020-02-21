# Minesweeperish
Minesweeperish is a clone of the classical Minesweeper game. It does not add any new features.
## Tutorial
## How-To
## Understanding
## Technical Reference
## To-do list
* Refactor the project into the Model-View-Presenter design pattern while leaving it in a working state after every commit.
    * Separate the MainGame class into smaller classes.
        * Separate data storage functionality into a Model class.
            * Create a Model Interface, and a class which implements it.
                * Make the functions empty at first, if necessary.
            * Move flags functionality from MainGame into this model class.
            * Move MineLocations functionality from MainGame into Model class.
            * Move Question mark functionality to Model class. 
        * Separate display logic into a separate View class.
            * Move logic for cell icons.
        * Change whatever is left of the MainGame class into a Presenter class.