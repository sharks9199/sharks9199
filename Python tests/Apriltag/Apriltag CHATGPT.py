import cv2

# Load image
img = cv2.videoCapture(0)

# Create AprilTag dictionary
dictionary = cv2.aruco.getPredefinedDictionary(cv2.aruco.DICT_APRILTAG_36h11)

# Detect markers
corners, ids, _ = cv2.aruco.detectMarkers(img, dictionary)

# Draw markers on image
#img = cv2.aruco.drawDetectedMarkers(img, corners, ids)

while True:
# Display image
    frame, cap = img.read()
    cv2.imshow("image", frame)

    
    if cv2.waitKey(1) & 0xff == ord("q"): #Se a tecla "Q" for apertada
        break #Quebra o "While True"

video.release() #Para de analisar o vídeo
cv2.destroyAllWindows #Fecha todas as janelas
