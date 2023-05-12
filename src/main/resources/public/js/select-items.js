const selectElem = document.querySelector('#product');
selectElem.addEventListener('change', (event) => {
const selectedOptions = selectElem.selectedOptions;
for (let i = 0; i < selectedOptions.length; i++) {
  const option = selectedOptions[i];
  option.setAttribute('selected', true);
}
});
