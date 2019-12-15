package paper_io_parts;

public class Ball {
    private double xPosition;
    private double yPosition;

    private double diameter;

    private double speedX;
    private double speedY;

    private double velocity;

    public Ball(int x, int y, int diameter, int speedX, int speedY) {
        this.xPosition = (double) x;
        this.yPosition = (double) y;
        this.diameter = (double) diameter;
        this.speedX = (double) speedX;
        this.speedY = (double) speedY;
        this.velocity = this.speedY;
    }

    public int getxPosition() {
        return (int) xPosition;
    }

    public int getyPosition() {
        return (int) yPosition;
    }

    public int getDiameter() {
        return (int) diameter;
    }

    public int getSpeedX() {
        return (int) speedX;
    }

    public int getSpeedY() {
        return (int) speedY;
    }

    public int getVelocity() {
        return (int) velocity;
    }

    public void update() {

        xPosition += speedX;

        yPosition += speedY;


    }

}
