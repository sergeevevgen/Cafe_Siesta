window.addEventListener("DOMContentLoaded", (event) => {
    // Get all the stars
    const stars = document.querySelectorAll(".star");

    // Add click event listener to each star
    stars.forEach((star) => {
        star.addEventListener("click", () => {
            stars.forEach((s) => s.classList.remove("active"));
            star.classList.add("active");

            const ratingInput = document.querySelector('input[name="rating-value"]');
            ratingInput.value = star.dataset.value;
            console.log("звезды", star.dataset.value);
            console.log(ratingInput.value);
        });
    });
});
