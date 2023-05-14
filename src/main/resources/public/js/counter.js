window.addEventListener("DOMContentLoaded", (event) => {
    const countText = document.getElementById("count"); // выбираем элемент, в котором хранится количество товаров
    const plusButton = document.getElementById("plus"); // выбираем кнопку увеличения количества товаров
    const minusButton = document.getElementById("minus"); // выбираем кнопку уменьшения количества товаров
    let count = 0;
    console.log(minusButton);
    console.log(plusButton);
    plusButton.addEventListener("click", function () { // добавляем обработчик события на клик по кнопке увеличения количества товаров
        console.log("Клик на кнопке 'минус'");
        if (count < 20) {
            count++; // увеличиваем количество товаров на 1
            countText.innerText = count; // обновляем количество товаров на странице
        }
    });

    minusButton.addEventListener("click", function () { // добавляем обработчик события на клик по кнопке уменьшения количества товаров
        console.log("Клик на кнопке 'плюс'");
        if (count > 0) { // проверяем, что количество товаров больше 0, чтобы не получить отрицательное количество
            count--; // уменьшаем количество товаров на 1
            countText.innerText = count; // обновляем количество товаров на странице
        }
    });
});