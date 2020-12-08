let input = require('./day2_input');

let lines = input.split('\n');

let part1 = (() => {
    let isValid = (line) => {
        let [range, value, password] = line.split(' ');
        value = value.substr(0, 1);
        let [min, max] = range.split('-');
        let occurences = password.split('').filter(letter => letter === value).length;
        return occurences >= min && occurences <= max;
    }

    let validCount = lines.reduce((prev, curr) => isValid(curr) ? prev + 1 : prev, 0);

    console.log('A', validCount);
})();


let part2 = (() => {
    let isValid = (line) => {
        let [range, value, password] = line.split(' ');
        value = value.substr(0, 1);
        let [pos1, pos2] = range.split('-');
        let val1 = password[pos1 - 1];
        let val2 = password[pos2 - 1];
        return (val1 === value || val2 === value) && val1 !== val2;
    }

    let validCount = 0;
    for (let line of lines) {
        validCount = isValid(line) ? validCount + 1 : validCount;
    }

    console.log('B', validCount);

})();

