window.addEventListener("DOMContentLoaded", (event) => {
  const productCards = document.querySelectorAll(".product-cart-pg");

    productCards.forEach((card) => {
    const countText = card.querySelector(".product-count-text");
    const plusButton = card.querySelector(".product-count.plus");
    const minusButton = card.querySelector(".product-count.minus");
    const priceText = card.querySelector(".info-price-cart-pg");
    const initialPrice = parseFloat(priceText.innerText);

    let count = 1;

    plusButton.addEventListener("click", function () {
      if (count < 10) {
        count++;
        countText.innerText = count;
        priceText.innerText = count * initialPrice;

        updateCartData(card.dataset.productId, count);
      }
    });

    minusButton.addEventListener("click", function () {
      if (count > 1) {
        count--;
        countText.innerText = count;
        priceText.innerText = count * initialPrice;

        updateCartData(card.dataset.productId, count);
      }
    });
    });

    function addProductToCart(id) {
      const url = `/orders/cart/${id}`;

      fetch(url, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error('Failed to add product to cart');
          }
          // В этом месте вы можете обновить интерфейс, если необходимо
          // например, обновить счетчик корзины или показать уведомление об успешном добавлении продукта
        })
        .catch((error) => {
          console.error('Error:', error);
          // Обработка ошибки, если необходимо
        });
    }
});
