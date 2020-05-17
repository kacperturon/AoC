let input = require("./input");
let {
  day
} = require("../utils");

function passwordValidator(checks) {
  return function (pass) {
    let digits = pass.toString().split('');
    return checks.every((check) => check(digits))
  }
}
function hasTwoAdjacentDigits(digits) {
  let counter = 1;
  for (let i = 1; i < digits.length; i++) {
    if (digits[i] === digits[i - 1]) {
      counter++;
    } else {
      if (counter == 2) return true;
      counter = 1;
    }
  }
  return counter == 2;
}

let hasAtLeastTwoAdjacentDigits = (digits) => digits.some((val, index, arr) => {
  if (index == 0) return false;
  return val === arr[index - 1];
})


let digitsLength = (digits) => digits.length == 6;

let digitsIncrease = (digits) => digits.every((val, index, arr) => {
  if (!arr[index + 1]) return true;
  return val <= arr[index + 1];
})


function part1() {
  let validPassword = passwordValidator([digitsLength, digitsIncrease, hasAtLeastTwoAdjacentDigits])
  let [minVal, maxVal] = input.split('-');
  let passwords = [];
  for (let i = minVal; i < maxVal; i++) {
    if (validPassword(i))
      passwords.push(i);
  }
  return passwords.length;
}

function part2() {
  let validPassword = passwordValidator([digitsLength, digitsIncrease, hasTwoAdjacentDigits])
  let [minVal, maxVal] = input.split('-');
  let passwords = [];
  for (let i = minVal; i < maxVal; i++) {
    if (validPassword(i))
      passwords.push(i);
  }
  return passwords.length;
}

day(part1, part2);
