import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator {
    private JFrame frame;
    private JTextField display;
    private double num1, num2;
    private String operator = ""; // 초기화

    public Calculator() {
        // 프레임 설정
        frame = new JFrame("윈도우 계산기");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);
        frame.setLayout(new BorderLayout());

        // 디스플레이 필드 설정
        display = new JTextField();
        display.setEditable(false);
        display.setBackground(Color.LIGHT_GRAY);
        frame.add(display, BorderLayout.NORTH);

        // 버튼 패널 설정
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4));

        // 숫자 및 연산자 버튼 생성
        String[] buttons = {
                "1", "2", "3", "+",
                "4", "5", "6", "-",
                "7", "8", "9", "*",
                "C", "0", "=", "/"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            try {
                if (command.charAt(0) >= '0' && command.charAt(0) <= '9') {
                    display.setText(display.getText() + command);
                } else if (command.equals("C")) {
                    display.setText("");
                    num1 = num2 = 0;
                    operator = ""; // 초기화
                } else if (command.equals("=")) {
                    num2 = Double.parseDouble(display.getText());
                    double result = performOperation(num1, num2, operator);
                    display.setText(String.format("%.2f", result));
                    num1 = result; // 결과를 num1로 저장하여 연속 계산 가능
                    operator = ""; // 연산자 초기화
                } else {
                    if (!operator.isEmpty()) {
                        num2 = Double.parseDouble(display.getText());
                        num1 = performOperation(num1, num2, operator);
                        display.setText(String.format("%.2f", num1));
                    } else {
                        num1 = Double.parseDouble(display.getText());
                    }
                    operator = command;
                    display.setText(""); // 다음 입력을 위해 디스플레이를 초기화
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "유효한 숫자를 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
            }
        }

        private double performOperation(double num1, double num2, String operator) {
            switch (operator) {
                case "+":
                    return num1 + num2;
                case "-":
                    return num1 - num2;
                case "*":
                    return num1 * num2;
                case "/":
                    if (num2 != 0) {
                        return num1 / num2;
                    } else {
                        JOptionPane.showMessageDialog(frame, "오류: 0으로 나눌 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                        return 0;
                    }
                default:
                    return 0;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calculator::new);
    }
}

