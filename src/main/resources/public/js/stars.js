// Get all the stars
const stars = document.querySelectorAll(".star");

// Add click event listener to each star
stars.forEach((star) => {
  star.addEventListener("click", () => {
    stars.forEach((s) => s.classList.remove("active"));
    star.classList.add("active");
  });
});

