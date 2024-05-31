document.addEventListener('DOMContentLoaded', (event) => {
    const inputField = document.querySelector('#inputIsbn');
    const oldValue = inputField.value;

    inputField.addEventListener('input', () => {
        const newValue = inputField.value;
        if (newValue === '' && newValue !== oldValue) {
            window.location.href = '/';
        }
    });
});
