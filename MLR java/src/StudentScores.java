import weka.core.Instances;
import weka.core.converters.CSVLoader;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.util.Arrays;
import javax.swing.*;
import java.awt.*;

import reg.ManualReg;

public class StudentScores {

    private static void plotScatterDiagramWithFittedLine(double[] hours, double[] scores, double beta_not,
            double beta_one) {
        XYSeries series = new XYSeries("Data");
        for (int i = 0; i < hours.length; i++) {
            series.add(hours[i], scores[i]);
        }

        XYSeries fittedLine = new XYSeries("Fitted Line");
        for (double hour : hours) {
            fittedLine.add(hour, beta_one * hour + beta_not);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        dataset.addSeries(fittedLine);

        JFreeChart chart = ChartFactory.createScatterPlot("Scatter Diagram of Hours and Scores with the Fitted Line",
                "Hours", "Scores",
                dataset);
        ChartPanel panel = new ChartPanel(chart);

        JFrame frame = new JFrame("Scatter Diagram of Hours and Scores with the Fitted Line");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    // Method to plot scatter diagram without fitted line
    private static void plotScatterDiagramWithoutFittedLine(double[] hours, double[] scores) {
        XYSeries series = new XYSeries("Data");
        for (int i = 0; i < hours.length; i++) {
            series.add(hours[i], scores[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createScatterPlot("Scatter Diagram of Hours and Scores", "Hours", "Scores",
                dataset);
        ChartPanel panel = new ChartPanel(chart);

        JFrame frame = new JFrame("Scatter Diagram of Hours and Scores");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    // Method to plot bar plot
    private static void plotBarPlot(double[] values, String title, String categoryAxisLabel, String valueAxisLabel) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < values.length; i++) {
            dataset.addValue(values[i], "Data", "Student " + (i + 1));
        }

        JFreeChart chart = ChartFactory.createBarChart(title, categoryAxisLabel, valueAxisLabel, dataset,
                PlotOrientation.VERTICAL, false, true, false);
        ChartPanel panel = new ChartPanel(chart);

        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            CSVLoader loader = new CSVLoader();

            loader.setSource(new File("score.csv"));

            Instances data = loader.getDataSet();

            double[] hours = new double[data.numInstances()];
            double[] scores = new double[data.numInstances()];

            for (int i = 0; i < data.numInstances(); i++) {
                hours[i] = data.instance(i).value(0);
                scores[i] = data.instance(i).value(1);
            }
            System.out.println(
                    "------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("DATA DESCRIPTION");
            System.out.println("Number of instances: " + data.numInstances());
            System.out.println("Number of attributes: " + data.numAttributes());
            System.out.println(
                    "------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("First few values of Hours:");
            for (int i = 0; i < Math.min(5, hours.length); i++) {
                System.out.println(hours[i]);
            }
            System.out.println(
                    "------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("First few values of Scores:");
            for (int i = 0; i < Math.min(5, scores.length); i++) {
                System.out.println(scores[i]);
            }
            System.out.println(
                    "------------------------------------------------------------------------------------------------------------------------------------------------------------");
            ManualReg suk = new ManualReg();

            // Calculating Slope and Intercept values
            System.out.println(
                    "------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("REGRESSION MODELLING");
            double beta_one = suk.beta_one(hours, scores);
            double beta_not = suk.beta_not(hours, scores);
            System.out.println("The coefficients of regression are ");
            System.out.println("The slope is " + beta_not);
            System.out.println("The intercept is " + beta_one);
            System.out.println("Our Regression model is ");
            System.out.println("Y = " + beta_not + " + " + beta_one + "*X");

            // Predicting Values of Scores
            System.out.println(
                    "------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("PREDICTION");
            double example = suk.predict(beta_not, beta_one, 4.7);
            System.out.println("The predicted score is " + example);

            double[] pred = new double[scores.length];
            for (int i = 0; i < scores.length; i++) {
                pred[i] = suk.predict(beta_not, beta_one, hours[i]);
            }
            System.out.println("The predicted values for hours are : ");
            System.out.println(Arrays.toString(pred));

            System.out.println(
                    "------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("EVALUATION METRICS");
            // Evaluating the model using R-Squared
            double r_squared = suk.r_squared(hours, scores, pred);
            System.out.println("The R-Squared value is " + r_squared);

            System.out.println(
                    "------------------------------------------------------------------------------------------------------------------------------------------------------------");

            plotScatterDiagramWithFittedLine(hours, scores, beta_not, beta_one);

            // Plotting Scatter Diagram without Fitted Line
            plotScatterDiagramWithoutFittedLine(hours, scores);

            // Plotting Bar Plot for Scores
            plotBarPlot(scores, "Scores", "Student", "Score");

            // Plotting Bar Plot for Hours
            plotBarPlot(hours, "Hours", "Student", "Hours");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
