package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/* ===================== NODE HUFFMAN ===================== */
class NodeH {
    char ch;
    int freq;
    NodeH left, right;

    NodeH(char ch, int freq) {
        this.ch = ch;
        this.freq = freq;
    }
}

/* ===================== GRAPH PANEL ===================== */
class GraphPanel extends JPanel {

    Map<String, Point> posisi = new HashMap<>();
    Map<String, List<int[]>> graph;
    Map<String, Integer> dist;

    GraphPanel() {
        setPreferredSize(new Dimension(350, 350));
        setBorder(BorderFactory.createTitledBorder("Visualisasi Graph"));
        setBackground(Color.WHITE);
    }

    void setData(Map<String, List<int[]>> graph, Map<String, Integer> dist) {
        this.graph = graph;
        this.dist = dist;
        generatePositions();
        repaint();
    }

    void generatePositions() {
        posisi.clear();
        int r = 120, cx = 170, cy = 170;
        int i = 0, n = graph.size();

        for (String node : graph.keySet()) {
            double angle = 2 * Math.PI * i / n;
            int x = cx + (int) (r * Math.cos(angle));
            int y = cy + (int) (r * Math.sin(angle));
            posisi.put(node, new Point(x, y));
            i++;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (graph == null) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));

        // ===== EDGE =====
        for (String u : graph.keySet()) {
            for (int[] v : graph.get(u)) {
                String to = String.valueOf((char) v[0]);
                Point p1 = posisi.get(u);
                Point p2 = posisi.get(to);

                g2.setColor(Color.LIGHT_GRAY);
                g2.drawLine(p1.x, p1.y, p2.x, p2.y);

                int mx = (p1.x + p2.x) / 2;
                int my = (p1.y + p2.y) / 2;
                g2.drawString(String.valueOf(v[1]), mx, my);
            }
        }

        // ===== NODE =====
        for (String n : posisi.keySet()) {
            Point p = posisi.get(n);

            g2.setColor(dist.get(n) == Integer.MAX_VALUE ? Color.GRAY : Color.RED);
            g2.fillOval(p.x - 15, p.y - 15, 30, 30);

            g2.setColor(Color.WHITE);
            g2.drawString(n, p.x - 4, p.y + 5);
        }
    }
}

/* ===================== GUI APP ===================== */
public class AlgoritmaApp extends JFrame {

    JComboBox<String> menu;
    JTextArea input, output;
    JButton proses;
    GraphPanel graphPanel;

    Font fontJudul = new Font("Segoe UI", Font.BOLD, 18);
    Font fontBiasa = new Font("Segoe UI", Font.PLAIN, 14);

    public AlgoritmaApp() {
        setTitle("Huffman & Dijkstra Visualization");
        setSize(1500, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        main.setBackground(new Color(245, 245, 245));
        add(main);

        JLabel title = new JLabel("Aplikasi Algoritma Huffman & Dijkstra", JLabel.CENTER);
        title.setFont(fontJudul);
        main.add(title, BorderLayout.NORTH);

        // ===== PANEL ATAS =====
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setBackground(new Color(245, 245, 245));

        menu = new JComboBox<>(new String[]{"Huffman", "Dijkstra"});
        menu.setFont(fontBiasa);

        proses = new JButton("Proses");
        proses.setFont(fontBiasa);
        proses.setBackground(new Color(0, 123, 255));
        proses.setForeground(Color.WHITE);

        top.add(new JLabel("Pilih Algoritma:"));
        top.add(menu);
        top.add(proses);

        main.add(top, BorderLayout.SOUTH);

        // ===== PANEL TENGAH =====
        JPanel center = new JPanel(new GridLayout(1, 3, 10, 10));

        input = new JTextArea();
        input.setFont(fontBiasa);
        input.setBorder(BorderFactory.createTitledBorder("Input"));

        output = new JTextArea();
        output.setFont(fontBiasa);
        output.setEditable(false);
        output.setBorder(BorderFactory.createTitledBorder("Output"));

        graphPanel = new GraphPanel();

        center.add(new JScrollPane(input));
        center.add(new JScrollPane(output));
        center.add(graphPanel);

        main.add(center, BorderLayout.CENTER);

        proses.addActionListener(e -> prosesAlgoritma());
    }

    /* ===================== HUFFMAN ===================== */
    void huffman(String text) {
        Map<Character, Integer> freq = new HashMap<>();
        for (char c : text.toCharArray())
            freq.put(c, freq.getOrDefault(c, 0) + 1);

        PriorityQueue<NodeH> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.freq));
        for (char c : freq.keySet())
            pq.add(new NodeH(c, freq.get(c)));

        while (pq.size() > 1) {
            NodeH l = pq.poll();
            NodeH r = pq.poll();
            NodeH p = new NodeH('\0', l.freq + r.freq);
            p.left = l;
            p.right = r;
            pq.add(p);
        }

        Map<Character, String> codes = new HashMap<>();
        generateCode(pq.peek(), "", codes);

        StringBuilder encoded = new StringBuilder();
        for (char c : text.toCharArray())
            encoded.append(codes.get(c));

        output.setText("Kode Huffman:\n");
        codes.forEach((k, v) -> output.append(k + " : " + v + "\n"));
        output.append("\nHasil Encoding:\n" + encoded);

        graphPanel.setData(null, null);
    }

    void generateCode(NodeH node, String code, Map<Character, String> map) {
        if (node == null) return;
        if (node.ch != '\0')
            map.put(node.ch, code);
        generateCode(node.left, code + "0", map);
        generateCode(node.right, code + "1", map);
    }

    /* ===================== DIJKSTRA ===================== */
    void dijkstra(String text) {
        String[] lines = text.split("\n");
        Map<String, List<int[]>> graph = new HashMap<>();

        for (String line : lines) {
            String[] p = line.split(" ");
            graph.putIfAbsent(p[0], new ArrayList<>());
            graph.putIfAbsent(p[1], new ArrayList<>());
            graph.get(p[0]).add(new int[]{p[1].charAt(0), Integer.parseInt(p[2])});
        }

        String start = String.valueOf(lines[0].charAt(0));
        Map<String, Integer> dist = new HashMap<>();
        for (String n : graph.keySet()) dist.put(n, Integer.MAX_VALUE);
        dist.put(start, 0);

        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingInt(dist::get));
        pq.add(start);

        while (!pq.isEmpty()) {
            String u = pq.poll();
            for (int[] v : graph.get(u)) {
                String node = String.valueOf((char) v[0]);
                int w = v[1];
                if (dist.get(u) + w < dist.get(node)) {
                    dist.put(node, dist.get(u) + w);
                    pq.add(node);
                }
            }
        }

        output.setText("Jarak Terpendek dari " + start + ":\n");
        dist.forEach((k, v) -> output.append(start + " → " + k + " = " + v + "\n"));

        graphPanel.setData(graph, dist);
    }

    /* ===================== PROSES ===================== */
    void prosesAlgoritma() {
        output.setText("");
        if (menu.getSelectedItem().equals("Huffman"))
            huffman(input.getText());
        else
            dijkstra(input.getText());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AlgoritmaApp().setVisible(true));
    }
}
