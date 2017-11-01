package mandrik.machine.learning.rosenblatt;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GraphicsFrame {


    private JPanel mainPanel;
    private JPanel commandPanel;
    private JPanel chartPanel;
    private JButton executeBtn;
    private JCheckBox adaptivStepCheckBox;
    private JTextField textField1;
    private JLabel labelStep;
    private JTextField textFieldX1;
    private JLabel answerLabel;
    private JTextField jTextFieldX2;
    private JLabel labelX1;
    private JLabel labelX2;
    private JLabel answer;
    private JButton buttonObraz;
    private JFrame frame;
    private ChartPanel currentChartPanel;


    PerseptronRosenblatt perseptronRosenblatt;

    public GraphicsFrame() {

        executeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentChartPanel != null) {
                    chartPanel.remove(currentChartPanel);
                }
                currentChartPanel = new ChartPanel(getChart());
                chartPanel.add(currentChartPanel);
                textFieldX1.setVisible(true);
                jTextFieldX2.setVisible(true);
                labelX1.setVisible(true);
                labelX2.setVisible(true);
                answerLabel.setVisible(true);
                answer.setVisible(true);
                buttonObraz.setVisible(true);
                chartPanel.updateUI();
            }
        });

        buttonObraz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double x1 = Double.parseDouble(textFieldX1.getText());
                double x2 = Double.parseDouble(jTextFieldX2.getText());

                Double y1 = perseptronRosenblatt.findY(x1, x2, perseptronRosenblatt.getT()[0]);
                Double y2 = perseptronRosenblatt.findY(x1, x2, perseptronRosenblatt.getT()[1]);
                answer.setText(y1.toString() + " " + y2.toString());
                answer.updateUI();
            }
        });

        adaptivStepCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textField1.isVisible()) {
                    textField1.setVisible(false);
                    labelStep.setVisible(false);
                } else {
                    textField1.setVisible(true);
                    labelStep.setVisible(true);
                }
                textField1.setText("0.5");
            }
        });

        textFieldX1.setVisible(false);
        jTextFieldX2.setVisible(false);
        labelX1.setVisible(false);
        labelX2.setVisible(false);
        answerLabel.setVisible(false);
        answer.setVisible(false);
        buttonObraz.setVisible(false);

        frame = new JFrame("MinimalStaticChart");
        frame.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 800);
    }

    public JFreeChart getChart() {

        double stepAducation = 0.5;
        boolean isAdaptive = true;
        if (textField1.isVisible()) {
            stepAducation = Double.parseDouble(textField1.getText());
            isAdaptive = false;
        }

        perseptronRosenblatt = new PerseptronRosenblatt(isAdaptive, stepAducation);

        perseptronRosenblatt.modulate();

        double[] w = perseptronRosenblatt.getW();
        double[] T = perseptronRosenblatt.getT();

        XYSeriesCollection xyDataset = new XYSeriesCollection();

        for(int index = 0; index < 2; index++) {
            XYSeries series = new XYSeries("S" + index);


            for (float i = -8; i < 8; i += 0.1) {
                series.add(i, (-w[0] * i + T[index]) / w[1]);
            }
            xyDataset.addSeries(series);
        }

        JFreeChart chart = ChartFactory
                .createXYLineChart("", "x", "y",
                        xyDataset,
                        PlotOrientation.VERTICAL,
                        true, true, true);
        XYPlot xyPlot = (XYPlot) chart.getPlot();
        ValueAxis domain = xyPlot.getDomainAxis();
        domain.setRange(-8, 8);
        ValueAxis range = xyPlot.getRangeAxis();
        range.setRange(-6, 6);
        return chart;
    }

    public static void main(String[] args) {

        GraphicsFrame graphicsFrame = new GraphicsFrame();

        graphicsFrame.frame.setVisible(true);
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.setBackground(new Color(-2497560));
        commandPanel = new JPanel();
        commandPanel.setLayout(new GridLayoutManager(11, 4, new Insets(0, 0, 0, 0), 1, -1));
        commandPanel.setBackground(new Color(-2497560));
        mainPanel.add(commandPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(200, -1), new Dimension(200, -1), new Dimension(200, -1), 1, false));
        adaptivStepCheckBox = new JCheckBox();
        adaptivStepCheckBox.setText("Адаптивный шаг обучения");
        commandPanel.add(adaptivStepCheckBox, new GridConstraints(0, 0, 2, 4, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 50), new Dimension(-1, 50), new Dimension(-1, 50), 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Шаг обучения:");
        commandPanel.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelStep = new JLabel();
        labelStep.setText("Шаг обучения:");
        commandPanel.add(labelStep, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        executeBtn = new JButton();
        executeBtn.setHorizontalAlignment(0);
        executeBtn.setText("Обучить");
        executeBtn.setVerticalAlignment(0);
        executeBtn.setVerticalTextPosition(0);
        commandPanel.add(executeBtn, new GridConstraints(3, 0, 5, 4, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 50), new Dimension(-1, 50), new Dimension(-1, 50), 0, false));
        textField1 = new JTextField();
        textField1.setHorizontalAlignment(4);
        textField1.setText("0.5");
        commandPanel.add(textField1, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        textFieldX1 = new JTextField();
        textFieldX1.setHorizontalAlignment(4);
        commandPanel.add(textFieldX1, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelX1 = new JLabel();
        labelX1.setText("x1:");
        commandPanel.add(labelX1, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelX2 = new JLabel();
        labelX2.setText("x2:");
        commandPanel.add(labelX2, new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jTextFieldX2 = new JTextField();
        jTextFieldX2.setHorizontalAlignment(4);
        commandPanel.add(jTextFieldX2, new GridConstraints(9, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        answerLabel = new JLabel();
        answerLabel.setText("y:");
        commandPanel.add(answerLabel, new GridConstraints(10, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        answer = new JLabel();
        answer.setText("None");
        commandPanel.add(answer, new GridConstraints(10, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        chartPanel = new JPanel();
        chartPanel.setLayout(new CardLayout(0, 0));
        chartPanel.setBackground(new Color(-13092031));
        mainPanel.add(chartPanel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(800, -1), new Dimension(800, -1), new Dimension(800, -1), 0, false));
        buttonObraz = new JButton();
        buttonObraz.setText("Подать образ");
        mainPanel.add(buttonObraz, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
