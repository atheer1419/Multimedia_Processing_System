/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multimedia_Processing_System;

import cpit380practice.FileChooser;
import cpit380practice.FrameSequencer;
import cpit380practice.MovieMaker;
import cpit380practice.Picture;
import cpit380practice.Pixel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author WinDows
 */
public class Movie extends javax.swing.JFrame {

    /**
     * Creates new form Movie
     */
    public Movie() {
        initComponents();  
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        TickerTape2 = new javax.swing.JLabel();
        sunset2 = new javax.swing.JLabel();
        EdgeDetection2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/photo_2020-12-06_11-03-08.jpg"))); // NOI18N

        TickerTape2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TickerTape2MouseClicked(evt);
            }
        });

        sunset2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sunset2MouseClicked(evt);
            }
        });

        EdgeDetection2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EdgeDetection2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(510, 510, 510)
                        .addComponent(sunset2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(510, 510, 510)
                        .addComponent(TickerTape2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(510, 510, 510)
                        .addComponent(EdgeDetection2, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1260, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(340, 340, 340)
                        .addComponent(sunset2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(90, 90, 90)
                        .addComponent(TickerTape2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(EdgeDetection2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 730, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TickerTape2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TickerTape2MouseClicked
        // TODO add your handling code here:
         try {
            String message = JOptionPane.showInputDialog("please enter your message");
            int framesPerSec = 30;
            Picture p = null;
            Graphics g = null;
            FrameSequencer frameSequencer = new FrameSequencer("Movie");
            Font font = new Font("Arial", Font.BOLD, 24);
            boolean right = true;
            // loop for 5 seconds of animation
            for (int j = 0, k = 0; j < framesPerSec *5; j++) {
                // draw the string
                p = new Picture(500, 400);
                g = p.getGraphics();
                g.setColor(Color.BLACK);
                g.setFont(font);
                if (right) {
                    if (k * 10 > p.getWidth() - message.length() * 12) {
                        right = false;
                    }
                } else {
                    if (k < 1) {
                        right = true;
                    }
                }
                if (right) {
                    g.drawString(message, k++ * 10, 200);
                } else {
                    g.drawString(message, k-- * 10, 200);
                }
                // add frame to sequencer
                frameSequencer.addFrame(p);
            }
            // play the movie
            frameSequencer.play(framesPerSec);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Time must be an integer", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_TickerTape2MouseClicked

    private void sunset2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sunset2MouseClicked
        // TODO add your handling code here:
           // TODO add your handling code here:
        MovieMaker movie = new MovieMaker();
        String directory = "sunset";
        
        String fName = FileChooser.pickAFile();
        Picture beachP = new Picture(fName);

        // declare other variables
        Picture target = null;
        FrameSequencer frameSequencer = new FrameSequencer(directory);
        int framesPerSec = 15;
        frameSequencer.setShown(true);

        // loop creating the frames
        for (int i = 0; i < framesPerSec ; i++) {
            beachP.makeSunset(100 - i );
            frameSequencer.addFrame(beachP);
        }

        // play the movie
        frameSequencer.play(framesPerSec);
    }//GEN-LAST:event_sunset2MouseClicked

    private void EdgeDetection2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EdgeDetection2MouseClicked
        // TODO add your handling code here:
          try {
            JFileChooser fc = new JFileChooser("edge");
            fc.showOpenDialog(null);
            String fName = fc.getSelectedFile().getAbsolutePath();
            Picture pic = new Picture(fName);

            Picture copyPict = null;
       
            FrameSequencer frameSequencer = new FrameSequencer("movie_Edit");
            int framesPerSec = 30;
            for (int i = 0; i < framesPerSec ; i++) {
                copyPict = new Picture(pic);
                double topAverage = 0.0;
                double bottomAverage = 0.0;
                for (int x = 0; x < pic.getHeight() - 1; x++) {
                    for (int y = 0; y < copyPict.getWidth(); y++) {
                        Pixel topPixel = copyPict.getPixel(y, x);
                        Pixel bottomPixel = copyPict.getPixel(y, x + 1);

                        topAverage = (topPixel.getRed() + topPixel.getGreen() + topPixel.getBlue()) / 3.0;
                        bottomAverage = (bottomPixel.getRed() + bottomPixel.getGreen() + bottomPixel.getBlue()) / 3.0;

                        if (Math.abs(topAverage - bottomAverage) < (framesPerSec + 1) - i) {
                            topPixel.setColor(Color.WHITE);
                        } else {
                            topPixel.setColor(Color.BLACK);
                        }
                    }
                }
               
                frameSequencer.addFrame(copyPict);
            }
            
            frameSequencer.play(framesPerSec);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Time must be an integer", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_EdgeDetection2MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Movie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Movie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Movie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Movie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Movie().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel EdgeDetection2;
    private javax.swing.JLabel TickerTape2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel sunset2;
    // End of variables declaration//GEN-END:variables
}