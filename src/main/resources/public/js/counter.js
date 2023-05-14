const countText = document.querySelector("#count"); // выбираем элемент, в котором хранится количество товаров
const plusButton = document.querySelector("#plus"); // выбираем кнопку увеличения количества товаров
const minusButton = document.querySelector("#minus"); // выбираем кнопку уменьшения количества товаров

let count = 0;
console.log(minusButton);
plusButton.addEventListener("click", function () { // добавляем обработчик события на клик по кнопке увеличения количества товаров
    console.log("Клик на кнопке 'минус'");
    if (count < 20) {
        count++; // увеличиваем количество товаров на 1
        countText.innerText = count; // обновляем количество товаров на странице
    }
});

minusButton.addEventListener("click", function () { // добавляем обработчик события на клик по кнопке уменьшения количества товаров
    console.log("Клик на кнопке 'Плюс'");
    if (count > 0) { // проверяем, что количество товаров больше 0, чтобы не получить отрицательное количество
        count--; // уменьшаем количество товаров на 1
        countText.innerText = count; // обновляем количество товаров на странице
    }
});