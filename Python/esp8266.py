import socket
import time

# Setup Wi-Fi server (like ESP8266's WiFiServer)
HOST = '192.168.151.1'  # Your PC's IP address (use ipconfig to find this)
PORT = 8080

# Create socket for server
server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.bind((HOST, PORT))
server_socket.listen(1)

print(f"[ESP8266-SIM] Listening on {HOST}:{PORT}...")

# Accept client connection
conn, addr = server_socket.accept()
print(f"[CLIENT CONNECTED] {addr}")

def send_telemetry():
    # Simulate GPS data (replace with data from Proteus/your GPS module)
    latitude = 54.071125
    longitude = -1.995948
    current_time = time.strftime("%Y-%m-%d %H:%M:%S", time.localtime())  # Get current time

    # Create the payload with latitude, longitude, and time
    payload = f"{latitude},{longitude},{current_time}\n"
    
    # Send data to Android app
    conn.sendall(payload.encode())

    # Print the data in the terminal for debugging
    print(f"[GPS] Sent: {payload.strip()}")

# Continuously send GPS data (simulate sending from Proteus)
try:
    while True:
        send_telemetry()
        time.sleep(1)  # Delay to simulate real-time data
except KeyboardInterrupt:
    print("[SERVER] Server stopped.")
finally:
    conn.close()
    server_socket.close()
