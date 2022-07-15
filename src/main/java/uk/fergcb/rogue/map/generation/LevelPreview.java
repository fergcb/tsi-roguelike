package uk.fergcb.rogue.map.generation;

import uk.fergcb.rogue.map.Direction;
import uk.fergcb.rogue.map.Level;
import uk.fergcb.rogue.map.rooms.Room;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;

/**
 * A quick GUI to visualise generated levels
 */
public class LevelPreview {

    /**
     * Generate a JPanel with the preview image and a button to regenerate the level
     * @return the preview JPanel
     */
    private static JPanel  generatePreviewPanel() {
        LevelGenerator gen = new LevelGenerator(16, 16);
        Level level = gen.generateLevel();

        int w = level.width();
        int h = level.height();
        int sw = w * 7;
        int sh = h * 7;
        int[] pixels = new int[h * w * 49];

        // Traverse the level, depth-first
        Iterator<Room> it = level.roomIterator();
        while (it.hasNext()) {
            drawRoom(it.next(), pixels, sw);
        }

        // Write the pixel array to an image and wrap it in a Swing icon
        BufferedImage tileImg = new BufferedImage(sw, sh, BufferedImage.TYPE_INT_RGB);
        tileImg.setRGB(0, 0, sw, sh, pixels, 0, sw);
        ImageIcon icon = new ImageIcon(tileImg.getScaledInstance(sw * 8, sh * 8, Image.SCALE_AREA_AVERAGING));

        // Create the panel
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(new JLabel(icon));

        JButton nextBtn = new JButton("Next");
        nextBtn.addActionListener(evt -> newLevel());
        panel.add(nextBtn);

        return panel;
    }

    /**
     * Write pixels in-place to illustrate a given room, in the appropriate location
     *
     * @param room the Room to draw
     * @param pixels the pixel buffer
     * @param w the width of the desired image in pixels
     */
    private static void drawRoom(Room room, int[] pixels, int w) {
        int ox = room.x * 7;
        int oy = room.y * 7;
        for (int dx = 1; dx < 6; dx++) {
            for (int dy = 1; dy < 6; dy++) {
                int xx = ox + dx;
                int yy = oy + dy;
                int i = yy * w + xx;
                pixels[i] = room.previewColor;
            }
        }
        // Draw doors
        if (room.hasExit(Direction.NORTH)) pixels[oy * w + ox + 3] = room.previewColor;
        if (room.hasExit(Direction.EAST))  pixels[(oy + 3) * w + ox + 6] = room.previewColor;
        if (room.hasExit(Direction.SOUTH)) pixels[(oy + 6) * w + ox + 3] = room.previewColor;
        if (room.hasExit(Direction.WEST))  pixels[(oy + 3) * w + ox] = room.previewColor;
    }

    /**
     * Generate a new preview panel, replace the old one and repaint the window
     */
    private static void newLevel() {
        if (currentPanel != null) frame.getContentPane().remove(currentPanel);
        JPanel newPanel = generatePreviewPanel();
        currentPanel = newPanel;
        frame.getContentPane().add(newPanel);
        frame.pack();
        frame.repaint();
    }

    private static JFrame frame;
    private static JPanel currentPanel;

    /**
     * LevelPreview application entry point
     * @param args command line args
     */
    public static void main(String[] args) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        newLevel();
        frame.setVisible(true);
    }
}
