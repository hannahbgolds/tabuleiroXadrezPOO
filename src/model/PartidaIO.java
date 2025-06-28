package model;

import javax.swing.*;

import java.awt.Component;
import java.io.*;
import java.util.Scanner;

public class PartidaIO {

    public static void salvar(ChessFacade modelo, Component parent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar Partida");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Arquivos de Texto (*.txt)", "txt"));

        if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
            File arquivo = fileChooser.getSelectedFile();
            if (!arquivo.getName().toLowerCase().endsWith(".txt")) {
                arquivo = new File(arquivo.getAbsolutePath() + ".txt");
            }

            try (PrintWriter out = new PrintWriter(new FileWriter(arquivo))) {
                // Salva o turno
                out.println(modelo.isWhiteTurn() ? "branco" : "preto");

                // Salva cada peça no formato: tipo,x,y,cor
                for (int x = 0; x < 8; x++) {
                    for (int y = 0; y < 8; y++) {
                        Piece p = modelo.getPieceAt(x, y);
                        if (p != null) {
                            String tipo = p.getClass().getSimpleName();
                            out.println(tipo + "," + x + "," + y + "," + (p.getColor() ? "branco" : "preto"));
                        }
                    }
                }

                JOptionPane.showMessageDialog(parent, "Partida salva com sucesso.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(parent, "Erro ao salvar: " + e.getMessage());
            }
        }
    }

    public static void carregar(ChessFacade modelo, Component parent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Carregar Partida");

        if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            File arquivo = fileChooser.getSelectedFile();

            try (Scanner scanner = new Scanner(arquivo)) {
                modelo.limparTabuleiro();

                if (!scanner.hasNextLine()) throw new IOException("Arquivo vazio!");
                String turno = scanner.nextLine().trim();
                modelo.setTurno(turno.equalsIgnoreCase("branco"));

                while (scanner.hasNextLine()) {
                    String linha = scanner.nextLine();
                    String[] dados = linha.split(",");
                    if (dados.length != 4) continue;

                    String tipo = dados[0];
                    int x = Integer.parseInt(dados[1]);
                    int y = Integer.parseInt(dados[2]);
                    boolean cor = dados[3].equalsIgnoreCase("branco");

                    modelo.adicionarPeca(tipo, x, y, cor);
                }

                JOptionPane.showMessageDialog(parent, "Partida carregada com sucesso.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(parent, "Erro ao carregar: " + e.getMessage());
            }
        }
    }
}

