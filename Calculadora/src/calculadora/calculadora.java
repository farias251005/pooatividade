package calculadora;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class calculadora {

    private static final String NOME = "Carlos Gabriel Faria dos Santos";
    private static final String RA = "0023536";
    private static final String NASCIMENTO = "25/10/2005";

    // Data: 25/10/2005 -> RGB (25, 10, 205)
    private static final Color AZUL = new Color(25, 10, 205);
    private static final Color FUNDO = new Color(247, 248, 250);
    private static final Color BOTAO = new Color(238, 240, 244);
    private static final Color OPERADOR = new Color(228, 226, 253);
    private static final Color TEXTO = new Color(43, 47, 58);

    private JTextField visor;
    private double primeiroNumero = 0;
    private String operacao = "";
    private boolean proximoNumero = true;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new calculadora().iniciar());
    }

    private void iniciar() {
        JFrame janela = new JFrame("Calculadora - " + NOME + " - RA " + RA);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setResizable(false);

        JPanel principal = new JPanel(new BorderLayout(0, 15));
        principal.setBackground(FUNDO);
        principal.setBorder(BorderFactory.createEmptyBorder(20, 20, 16, 20));

        JPanel topo = new JPanel(new BorderLayout(0, 12));
        topo.setOpaque(false);
        JLabel titulo = new JLabel("Calculadora");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(TEXTO);
        topo.add(titulo, BorderLayout.NORTH);

        visor = new JTextField("0");
        visor.setEditable(false);
        visor.setHorizontalAlignment(SwingConstants.RIGHT);
        visor.setFont(new Font("Arial", Font.BOLD, 32));
        visor.setForeground(TEXTO);
        visor.setBackground(Color.WHITE);
        visor.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 225, 232), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        visor.setPreferredSize(new Dimension(305, 70));
        topo.add(visor, BorderLayout.CENTER);
        principal.add(topo, BorderLayout.NORTH);

        JPanel teclado = new JPanel(new GridBagLayout());
        teclado.setOpaque(false);

        adicionar(teclado, "C", 0, 0, 1, 1);
        adicionar(teclado, "^", 1, 0, 1, 1);
        adicionar(teclado, "/", 2, 0, 1, 1);
        adicionar(teclado, "*", 3, 0, 1, 1);

        adicionar(teclado, "7", 0, 1, 1, 1);
        adicionar(teclado, "8", 1, 1, 1, 1);
        adicionar(teclado, "9", 2, 1, 1, 1);
        adicionar(teclado, "-", 3, 1, 1, 1);

        adicionar(teclado, "4", 0, 2, 1, 1);
        adicionar(teclado, "5", 1, 2, 1, 1);
        adicionar(teclado, "6", 2, 2, 1, 1);
        adicionar(teclado, "+", 3, 2, 1, 1);

        adicionar(teclado, "1", 0, 3, 1, 1);
        adicionar(teclado, "2", 1, 3, 1, 1);
        adicionar(teclado, "3", 2, 3, 1, 1);
        adicionar(teclado, "=", 3, 3, 1, 2);

        adicionar(teclado, "0", 0, 4, 2, 1);
        adicionar(teclado, ".", 2, 4, 1, 1);
        principal.add(teclado, BorderLayout.CENTER);

        JLabel rodape = new JLabel(NOME + "  |  RA: " + RA + "  |  " + NASCIMENTO);
        rodape.setFont(new Font("Arial", Font.PLAIN, 10));
        rodape.setForeground(new Color(101, 107, 122));
        rodape.setHorizontalAlignment(SwingConstants.CENTER);
        principal.add(rodape, BorderLayout.SOUTH);

        janela.setContentPane(principal);
        janela.pack();
        janela.setLocationRelativeTo(null);
        janela.setVisible(true);
    }

    private void adicionar(JPanel painel, String texto, int coluna, int linha, int colunas, int linhas) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 20));
        botao.setFocusPainted(false);
        botao.setOpaque(true);
        botao.setContentAreaFilled(true);
        botao.setBorder(BorderFactory.createEmptyBorder());
        botao.setPreferredSize(new Dimension(68, 55));

        if ("=".equals(texto)) {
            botao.setBackground(AZUL);
            botao.setForeground(Color.WHITE);
        } else if ("C".equals(texto)) {
            botao.setBackground(new Color(255, 230, 232));
            botao.setForeground(new Color(157, 49, 64));
        } else if ("^/*-+".contains(texto)) {
            botao.setBackground(OPERADOR);
            botao.setForeground(AZUL);
        } else {
            botao.setBackground(BOTAO);
            botao.setForeground(TEXTO);
        }

        botao.addActionListener(_ -> pressionar(texto));
        GridBagConstraints pos = new GridBagConstraints();
        pos.gridx = coluna;
        pos.gridy = linha;
        pos.gridwidth = colunas;
        pos.gridheight = linhas;
        pos.fill = GridBagConstraints.BOTH;
        pos.weightx = 1;
        pos.weighty = 1;
        pos.insets = new Insets(4, 4, 4, 4);
        painel.add(botao, pos);
    }

    private void pressionar(String tecla) {
        if (tecla.matches("[0-9]")) {
            if (proximoNumero || "0".equals(visor.getText()) || "Erro".equals(visor.getText())) {
                visor.setText(tecla);
                proximoNumero = false;
            } else {
                visor.setText(visor.getText() + tecla);
            }
        } else if (".".equals(tecla)) {
            if (proximoNumero || "Erro".equals(visor.getText())) {
                visor.setText("0.");
                proximoNumero = false;
            } else if (!visor.getText().contains(".")) {
                visor.setText(visor.getText() + ".");
            }
        } else if ("C".equals(tecla)) {
            visor.setText("0");
            primeiroNumero = 0;
            operacao = "";
            proximoNumero = true;
        } else if ("=".equals(tecla)) {
            calcular();
        } else if ("+-*/^".contains(tecla)) {
            if ("Erro".equals(visor.getText())) return;
            if (!operacao.isEmpty() && !proximoNumero) {
                calcular();
                if ("Erro".equals(visor.getText())) return;
            }
            primeiroNumero = Double.parseDouble(visor.getText());
            operacao = tecla;
            proximoNumero = true;
        }
    }

    // Cinco operacoes da calculadora.
    private double adicao(double a, double b) { return a + b; }
    private double subtracao(double a, double b) { return a - b; }
    private double multiplicacao(double a, double b) { return a * b; }
    private double divisao(double a, double b) {
        if (b == 0) throw new ArithmeticException("Divisao por zero");
        return a / b;
    }
    private double potencia(double a, double b) { return Math.pow(a, b); }

    private void calcular() {
        if (operacao.isEmpty() || proximoNumero) return;
        double segundoNumero = Double.parseDouble(visor.getText());
        try {
            double resultado;
            switch (operacao) {
                case "+": resultado = adicao(primeiroNumero, segundoNumero); break;
                case "-": resultado = subtracao(primeiroNumero, segundoNumero); break;
                case "*": resultado = multiplicacao(primeiroNumero, segundoNumero); break;
                case "/": resultado = divisao(primeiroNumero, segundoNumero); break;
                case "^": resultado = potencia(primeiroNumero, segundoNumero); break;
                default: return;
            }
            if (!Double.isFinite(resultado)) throw new ArithmeticException("Resultado invalido");
            if (resultado == Math.rint(resultado) && resultado >= Long.MIN_VALUE && resultado < 0x1.0p63) {
                visor.setText(String.valueOf((long) resultado));
            } else {
                visor.setText(String.valueOf(resultado));
            }
            primeiroNumero = resultado;
        } catch (ArithmeticException erro) {
            visor.setText("Erro");
            primeiroNumero = 0;
        }
        operacao = "";
        proximoNumero = true;
    }
}
