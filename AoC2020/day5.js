
let input = require('./day5_input');

let findValue = (value, min, max, upperHalfChar) => {
    let chars = value.split('');
    for(let char of chars){
        if(char===upperHalfChar){
            min=min+Math.round((max-min)/2);
        }else max=max-Math.round((max-min)/2);
    }
    return min;
}

let findColumn = (value) => {
    let val = value.slice(-3);
    return findValue(val, 0, 7, 'R');
}

let findRow = (value) => {
    let val = value.slice(0, 7);
    return findValue(val, 0, 127, 'B');
}

let getId = (row, column) => row * 8 + column;


let highestId = Number.MIN_VALUE;
for(let val of input.split('\n')){
    let id = getId(findRow(val), findColumn(val));
    if(id>highestId) highestId = id;
}

console.log(highestId);

let occupiedSeats = {};
Array(128).fill(0).forEach((_,i)=>Array(8).fill(0).forEach((_,j)=>occupiedSeats[`${i} ${j}`]=false));
console.log(occupiedSeats);
for(let val of input.split('\n')){
    let row = findRow(val);
    let col = findColumn(val);
    let id = getId(row, col);
    occupiedSeats[`${row} ${col}`]=true;
}
console.log(occupiedSeats);
let freeSeats = Object.keys(occupiedSeats).filter((k)=>occupiedSeats[k]===true);

console.log(freeSeats);