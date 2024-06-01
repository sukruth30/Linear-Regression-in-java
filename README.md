# Simple Linear Regression Java Project

## Description
This project performs Simple Linear Regression (SLR) analysis on student scores and study hours using Java. It includes manual regression calculations, data handling with WEKA, and visualizations with JFreeChart.

## Features
- Manual calculation of mean, slope (beta_one), intercept (beta_not), prediction, and R-squared.
- Data loading from CSV using WEKA.
- Visualization of data and regression results using JFreeChart (scatter plots and bar charts).

## Setup

### Dependencies
- [WEKA](https://www.cs.waikato.ac.nz/ml/weka/)
- [JFreeChart](https://sourceforge.net/projects/jfreechart/)

### Data
- Place your dataset (e.g., `score.csv`) in the project directory.

## Running the Project

### Compile
javac -cp weka.jar:jfreechart.jar:. StudentScores.java

### Run 
java -cp weka.jar:jfreechart.jar:. StudentScores

### Files:- 
#### ManualReg.java: Implements linear regression calculations.
#### StudentScores.java: Handles data loading, regression execution, and visualizations.

### Example Output
#### Console: Displays data summary, regression coefficients, model, predicted values, and R-squared value.
#### GUI: Shows scatter plots with/without fitted lines and bar plots for scores and hours.

### Usage
This project is suitable for educational purposes, demonstrating regression analysis, data visualization, and integration of multiple Java libraries.
