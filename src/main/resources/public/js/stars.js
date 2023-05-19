window.addEventListener("DOMContentLoaded", (event) => {
  // Get all the stars
  const stars = document.querySelectorAll(".star");
  const ratingInput = document.querySelector('#rating-value');

  // Set initial selected stars based on the rating input value
  stars.forEach((star) => {
    if (star.dataset.value <= ratingInput.value) {
      star.classList.add("active");
    }
  });

  // Add click event listener to each star
  stars.forEach((star) => {
    star.addEventListener("click", () => {
      const selectedValue = star.dataset.value;

      stars.forEach((s) => {
        if (s.dataset.value <= selectedValue) {
          s.classList.add("active");
        } else {
          s.classList.remove("active");
        }
      });

      ratingInput.value = selectedValue;
    });
  });
});
