function updateImageName() {
    const imageInput = document.getElementById('image-input');
    const imageNameInput = document.getElementById('image');

    if (imageInput.files && imageInput.files.length > 0) {
        const imageName = imageInput.files[0].name;
        imageNameInput.value = imageName;
    }
}