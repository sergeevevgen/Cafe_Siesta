window.addEventListener("DOMContentLoaded", (event) => {
    const countText = document.getElementById("count"); // выбираем элемент, в котором хранится количество товаров
    const plusButton = document.getElementById("plus"); // выбираем кнопку увеличения количества товаров
    const minusButton = document.getElementById("minus"); // выбираем кнопку уменьшения количества товаров
    const priceText = document.querySelector(".price-pr-pg"); // выбираем элемент, в котором хранится цена товара
    const initialPrice = parseFloat(priceText.innerText); // сохраняем начальную цену товара

    let count = 1;

    plusButton.addEventListener("click", function () { // добавляем обработчик события на клик по кнопке увеличения количества товаров
        console.log("Клик на кнопке 'минус'");
        if (count < 10) {
            count++; // увеличиваем количество товаров на 1
            countText.innerText = count; // обновляем количество товаров на странице
            priceText.innerText = count * initialPrice; // обновляем цену товара на странице
        }
    });

    minusButton.addEventListener("click", function () { // добавляем обработчик события на клик по кнопке уменьшения количества товаров
        console.log("Клик на кнопке 'плюс'");
        if (count > 1) { // проверяем, что количество товаров больше 0, чтобы не получить отрицательное количество
            count--; // уменьшаем количество товаров на 1
            countText.innerText = count; // обновляем количество товаров на странице

            if (count === 0) {
                priceText.innerText = initialPrice;
            } else {
                priceText.innerText = count * initialPrice;
            }
        }
    });
});
