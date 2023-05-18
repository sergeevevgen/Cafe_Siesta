window.addEventListener("DOMContentLoaded", (event) => {
    const productCards = document.querySelectorAll(".product-cart-pg");

    productCards.forEach((card) => {
        const countText = card.querySelector(".product-count-text");
        const plusButton = card.querySelector(".product-count.plus");
        const minusButton = card.querySelector(".product-count.minus");
        const priceText = card.querySelector(".info-price-cart-pg");
        const productId = parseInt(countText.dataset.id, 10);
        const initialPrice = parseFloat(priceText.innerText);

        console.log(countText.dataset);

        let count = 1;

        plusButton.addEventListener("click", function () {
            if (count < 10) {
                count++;
                countText.innerText = count;
                priceText.innerText = count * initialPrice;
                updateProductCount(productId, count);
            }
        });

        minusButton.addEventListener("click", function () {
            if (count > 1) {
                count--;
                countText.innerText = count;
                priceText.innerText = count * initialPrice;
                updateProductCount(productId, count);
            }
        });
    });

    function updateProductCount(productId, count) {
        $.ajax({
            url: '/orders/cart/add/' + productId,
            type: 'POST',
            data: {
                count: count
            },
            success: function(response) {
                console.log('Количество продукта обновлено.');
            },
            error: function(error) {
                console.error('Произошла ошибка при обновлении количества продукта:', error);
            }
        });
    }
});
