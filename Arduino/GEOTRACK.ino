#include <Servo.h>
#include <TinyGPS++.h>
#include <Wire.h>
#include <LiquidCrystal_I2C.h>

// === Objects and Pins ===
Servo myServo;
TinyGPSPlus gps;
LiquidCrystal_I2C lcd(0x20, 16, 2); // LCD I2C address
String inputID = "";
String validID = "123456789";
bool gpsDone = false;

void setup() {
  myServo.attach(9);
  myServo.write(0); // Locked

  Serial.begin(9600);        // RFID input
  Serial1.begin(9600);       // GPS module input

  lcd.begin(16, 2);
  lcd.init();
  lcd.backlight();
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("Ready to Scan");

  Serial.println("Scan RFID:");
}

void loop() {
  // Read RFID input
  if (Serial.available()) {
    char c = Serial.read();

    if (c == '\n' || c == '\r') {
      Serial.print("Received: ");
      Serial.println(inputID);

      if (inputID == validID) {
        Serial.println("Access Granted");
        unlockDoor();

        if (waitForGPS()) {
          displayGPS();
        }

        Serial.println("\nScan RFID:");
        lcd.clear();
        lcd.print("Ready to Scan");
      } else {
        Serial.println("Access Denied");
        Serial.println(" ");
        lcd.clear();
        lcd.print("Access Denied");
        delay(2000);
        lcd.clear();
        lcd.print("Ready to Scan");
        Serial.println("\nScan RFID:");
      }

      inputID = ""; // Clear RFID input
    } else {
      inputID += c;
    }
  }

  // Keep feeding GPS parser
  while (Serial1.available()) {
    gps.encode(Serial1.read());
  }
}

void unlockDoor() {
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("Access Granted");

  myServo.write(90); // Unlock
  delay(3000);
  myServo.write(0);  // Lock
  lcd.clear();
  lcd.print("Locked");
  delay(1000);
}

bool waitForGPS() {
  unsigned long start = millis();
  while (millis() - start < 5000) { // Wait max 5 seconds
    while (Serial1.available()) {
      gps.encode(Serial1.read());
    }

    if (gps.location.isValid() && gps.date.isValid() && gps.time.isValid()) {
      return true;
    }
  }
  return false;
}
void displayGPS() {
  double lat = gps.location.lat();
  double lng = gps.location.lng();

  // Serial Debug
  Serial.println("GPS Data:");
  Serial.print("Lat: "); Serial.println(lat, 6);
  Serial.print("Lng: "); Serial.println(lng, 6);
  Serial.print("Date: "); Serial.print(gps.date.month());
  Serial.print("/"); Serial.print(gps.date.day());
  Serial.print("/"); Serial.println(gps.date.year());
  Serial.print("Time: "); Serial.print(gps.time.hour());
  Serial.print(":"); Serial.print(gps.time.minute());
  Serial.print(":"); Serial.println(gps.time.second());
  Serial.println(" ");

  // LCD Display 1: Coordinates
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("Lat:");
  lcd.print(lat, 2);  // Trimmed for LCD fit
  
  delay(3000);

  lcd.setCursor(0, 0);
  lcd.print("Lng:");
  lcd.print(lng, 2);  // Trimmed for LCD fit

  delay(3000);

  // LCD Display 2: Date/Time
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("Date:");
  lcd.print(gps.date.month());
  lcd.print("/");
  lcd.print(gps.date.day());

  delay(3000);
  
  lcd.setCursor(0, 0);
  lcd.print("Time:");
  if (gps.time.hour() < 10) lcd.print("0");
  lcd.print(gps.time.hour());
  lcd.print(":");
  if (gps.time.minute() < 10) lcd.print("0");
  lcd.print(gps.time.minute());

  delay(3000);
}
