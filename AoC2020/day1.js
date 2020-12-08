let input = require('./day1_input');

input = input.split('\n').map(Number);

const sum = 2020;

let inputReminder = {};

for(let i of input){
    inputReminder[i] = sum - i;
}

//2. loop through keys and find our sum
for(let x in inputReminder){
    x=Number(x);
    if(inputReminder[inputReminder[x]]){
        console.log(x, inputReminder[x], x*inputReminder[x]);
        return;
    }
}


for(let i=0; i<input.length;i++){
    let x=input[i];
    let curr_sum = sum - x;
    let set = [];
    for(let j=i+1; j<input.length; j++){
        if(set.includes(curr_sum - input[j])){
            console.log(input[i], input[j], curr_sum-input[j]);
            console.log(input[i] * input[j] * (curr_sum-input[j]));
            return;
        }
        set.push(input[j]);
    }

}