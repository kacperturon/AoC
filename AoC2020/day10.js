let input = require('./day10_input');

// input = `28
// 33
// 18
// 42
// 31
// 14
// 46
// 20
// 48
// 47
// 24
// 23
// 49
// 45
// 19
// 38
// 39
// 11
// 1
// 32
// 25
// 35
// 8
// 17
// 7
// 9
// 4
// 2
// 34
// 10
// 3`;

let vals = [0, ...input.split('\n').map(Number).sort((a,b)=>a-b)];

let threes = 1;
let ones = 1;

for(let i=1; i<vals.length; i++){
    let prev = vals[i-1];
    let curr = vals[i];
    if(curr-prev===3)threes++;
    if(curr-prev===1)ones++;
}

console.log('A', threes*ones);

console.log(vals);
moves=[1,2,3];
let diffRoutes = 0;
function calculateMoves(i){
    let val = vals[i];
    if(i===vals.length-1){
        diffRoutes++;
        return;
    }
    if(vals[i+1] && moves.includes(vals[i+1]-val)) calculateMoves(i+1);
    if(vals[i+2] && moves.includes(vals[i+2]-val)) calculateMoves(i+2);
    if(vals[i+3] && moves.includes(vals[i+3]-val)) calculateMoves(i+3);

}

// console.log(calculateMoves(0));
// console.log('B',diffRoutes);
console.log(vals.length);
diffRoutes=0;

function calculateMoves2(){
    let seen = [];
    seen.push(0);
    while(seen.length>0){
        let i = seen.pop();
        let val = vals[i];
        if(i===vals.length-1){
            diffRoutes++;
            // console.log(diffRoutes);
            continue;
        }
        if(vals[i+1] && moves.includes(vals[i+1]-val)) seen.push(i+1);
        if(vals[i+2] && moves.includes(vals[i+2]-val)) seen.push(i+2);
        if(vals[i+3] && moves.includes(vals[i+3]-val)) seen.push(i+3);

    }
}
calculateMoves2();
console.log(diffRoutes);