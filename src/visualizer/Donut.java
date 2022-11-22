package visualizer;

import java.awt.Graphics2D;

/**
 * 
 * @author Nuno Fonseca
 */

class Donut {
    
    
    char[][] screen;
    float [][] zBuffer;
    
    float PI = (float)Math.PI;
    
    int screen_width, screen_height;
    float R1 = .9f; // Circle Radius
    float R2 = 2; // Torus Radius
    float K2 = 5, K1; // Distance from viewer to the Donut
    

    float xRotationSpeed = 0.8f / 20; 
    float zRotationSpeed = 0.2f / 20; 
    
    float theta_increment = 0.07f;
    float phi_increment = 0.02f;
    
    float A = 0, B = 0;

    public Donut(int screen_width, int screen_height) {
        this.screen_width = screen_width;
        this.screen_height = screen_height;
        
        screen = new char[screen_width][screen_height];
        zBuffer = new float[screen_width][screen_height];
        
        float ref = screen_width > screen_height ? screen_width : screen_height;
        K1 = (3f * ref  * K2) / (8f * (R1 + R2)); // Scale (distance from viewer to the 2d screen) 3/8th
        K1 = 25;
    }
    

    void draw(Screen display, Graphics2D g2d){
        
        calculate(A, B, display, g2d);
         
        A += xRotationSpeed;
        B += zRotationSpeed;  
    }
    
    void calculate(float A, float B, Screen display, Graphics2D g2d){
        // A - X Rotation, B - Z rotation
        float x, y, z, sinTheta, cosTheta, sinPhi, cosPhi, circleX, circleY, ooz, L;
        float sinA = sin(A), cosA = cos(A), sinB = sin(B), cosB = cos(B), brightness = 1;
        int xp, yp, luminanceIndex;
        
        for (float theta = 0; theta < 2 * PI; theta += theta_increment) {
            sinTheta = sin(theta); cosTheta = cos(theta);
            
            for (float phi = 0; phi < 2 * PI; phi += phi_increment) {
                sinPhi = sin(phi); cosPhi = cos(phi);
                
                circleX = R2 + R1 * cosTheta;
                circleY = R1 * sinTheta;
                
                // 3D Point
                x = circleX*(cosB*cosPhi+sinA*sinB*sinPhi)-circleY*cosA*sinB;
                y = circleX*(cosPhi*sinB-cosB*sinA*sinPhi)+circleY*cosA*cosB;
                z = K2 + circleX*cosA*sinPhi + circleY*sinA;
                
                ooz = 1 / z;
                
                // Projected 2D Point
                xp = (int)(screen_width / 2 + K1 * x * ooz);
                yp = (int)(screen_height / 2 - K1 * y * ooz);
                
                // Luminance
                L = cosPhi*cosTheta*sinB - cosA*cosTheta*sinPhi - sinA*sinTheta + cosB*(cosA*sinTheta - cosTheta*sinA*sinPhi);
                
                if (L > 0){
                    
                    if (ooz > zBuffer[xp][yp]){
                        zBuffer[xp][yp] = ooz;
                        luminanceIndex = (int)(L * 8) % 12;
                        //screen[xp][yp] = ".,-~:;=!*#$@".charAt(luminanceIndex);
                    }
                    
                }
               
                display.plotPoint(xp, yp, g2d);
            }
        }
    }
    
    
    float sin(float angle){
        return (float)Math.sin(angle);
    }
    
    float cos(float angle){
        return (float)Math.cos(angle);
    }
    
}

