# We need a JDK 11 image, so specify that we want to build from a JDK 11 image

# Specify a default work directory where the app and its files will be stored

# Copy your packaged JAR file into your image

# Specify an entrypoint on which executable your image should start on. We are building an image based on a Java application so we will need to use "java".