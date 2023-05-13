import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private JButton botonIniciar, botonDetener, botonReiniciar, botonEliminar, botonSalir;
    private JLabel carro, carretera;
    private Hilo hilo;
    private int posInicial;
    private JSlider sliderVelocidad;

    public Main() {
        super("Carro con hilos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        int anchoPantalla = Toolkit.getDefaultToolkit().getScreenSize().width;
        int altoPantalla = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setUndecorated(true);
        this.setBounds(0, 0, anchoPantalla, altoPantalla);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon imagenCarretera = new ImageIcon(getClass().getResource("/Imagenes/carretera.jpg"));
        carretera = new JLabel(imagenCarretera);
        carretera.setLayout(null);

        ImageIcon imagenCarro = new ImageIcon(getClass().getResource("/Imagenes/carro.png"));
        carro = new JLabel(imagenCarro);
        carro.setSize(imagenCarro.getIconWidth(), imagenCarro.getIconHeight());
        carro.setLocation(0, 200);

        hilo = new Hilo(carro);
        posInicial = carro.getX();

        // Crear el JSlider y su JLabel correspondiente
        sliderVelocidad = new JSlider(JSlider.HORIZONTAL, 0, 300, 25);
        sliderVelocidad.setMajorTickSpacing(10);
        sliderVelocidad.setMinorTickSpacing(5);
        sliderVelocidad.setPaintTicks(true);
        sliderVelocidad.setPaintLabels(true);
        JLabel labelVelocidad = new JLabel("Velocidad: ");
        JPanel panelSlider = new JPanel();
        panelSlider.setLayout(new BorderLayout());
        panelSlider.add(labelVelocidad, BorderLayout.WEST);
        panelSlider.add(sliderVelocidad, BorderLayout.CENTER);

        botonIniciar = new JButton("Iniciar");
        botonIniciar.addActionListener(e -> hilo.iniciar());
        botonDetener = new JButton("Detener");
        botonDetener.addActionListener(e -> hilo.detener());
        botonReiniciar = new JButton("Reiniciar");
        botonReiniciar.addActionListener(e -> hilo.reiniciar());
        botonEliminar = new JButton("Eliminar");
        botonEliminar.addActionListener(e -> hilo.eliminar());
        botonSalir = new JButton("Salir");
        botonSalir.addActionListener(e -> System.exit(0));

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1, 5));
        panelBotones.add(botonIniciar);
        panelBotones.add(botonDetener);
        panelBotones.add(botonReiniciar);
        panelBotones.add(botonEliminar);
        panelBotones.add(botonSalir);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.add(panelSlider, BorderLayout.NORTH);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        panelPrincipal.add(carretera, BorderLayout.CENTER);

        carretera.add(carro);
        getContentPane().add(panelPrincipal);

        setVisible(true);
    }


    public static void main(String[] args) {
        Main interfaz = new Main();
    }

    private class Hilo extends Thread {
        private JLabel carro;
        private boolean detener = false;

        public Hilo(JLabel carro) {
            this.carro = carro;
        }

        public void detener() {
            detener = true;
        }

        public void reiniciar() {
            detener = true;
            carro.setLocation(posInicial, carro.getY());
        }

        public void eliminar() {
            detener = true;
            carro.setVisible(false);
        }
        

        private void moverCarro() {
            int x = carro.getX();
            int y = carro.getY();

            x += sliderVelocidad.getValue() / 2; // Ajustar la velocidad

            if (x > getWidth()) {
                x = -carro.getWidth();
            }

            carro.setLocation(x, y);
        }

        @Override
        public void run() {
            while (true) {
                if (!detener) {
                    moverCarro();
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }

        public void iniciar() {
            if (!this.isAlive()) {
                this.start();
            }
            else{
                detener = false;
                if (!carro.isVisible()) {
                    carro.setVisible(true);
                    moverCarro();
                }
            }
        }
    }
}