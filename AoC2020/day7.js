// let input = `light red bags contain 1 bright white bag, 2 muted yellow bags.
// dark orange bags contain 3 bright white bags, 4 muted yellow bags.
// bright white bags contain 1 shiny gold bag.
// muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
// shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
// dark olive bags contain 3 faded blue bags, 4 dotted black bags.
// vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
// faded blue bags contain no other bags.
// dotted black bags contain no other bags.`;

// let input = `shiny gold bags contain 2 dark red bags.
// dark red bags contain 2 dark orange bags.
// dark orange bags contain 2 dark yellow bags.
// dark yellow bags contain 2 dark green bags.
// dark green bags contain 2 dark blue bags.
// dark blue bags contain 2 dark violet bags.
// dark violet bags contain no other bags.`;

let input = require('./day7_input.js');

const bagContents = {};

let rules = input.split('\n');

for(let rule of rules){
    let [outerBag, innerBags] = rule.split('contain');
    outerBag = outerBag.trim();
    let outerBagWords = outerBag.split(' ');
    let outerBagKey = `${outerBagWords[0]} ${outerBagWords[1]}`
    bagContents[outerBagKey] = {};
    innerBags = innerBags.trim().split(',');
    for(let innerBag of innerBags){
        let innerBagWords = innerBag.trim().split(' ');
        let innerBagCount = Number(innerBagWords[0]) || 0;
        let innerBagKey = `${innerBagWords[1]} ${innerBagWords[2]}`
        bagContents[outerBagKey][innerBagKey] = innerBagCount;
    }
    
}

function countTarget(outerBag, outerBagCount, target){
    let sum = 0;
    if(!bagContents[outerBag]) return 0;
    for(let innerBag of Object.keys(bagContents[outerBag])){
        let innerBagCount = bagContents[outerBag][innerBag];
        if(innerBag===target){
            sum+=innerBagCount*outerBagCount;
        }else{
            sum+=countTarget(innerBag, outerBagCount*innerBagCount, target);
        }
    }
    return sum;
}

let bagColoursCount = 0;
const bagTarget = 'shiny gold';

for(let key of Object.keys(bagContents)){
    if(countTarget(key, 1, bagTarget)>0) bagColoursCount++;
}
console.log(bagColoursCount);

function countTarget2(outerBag, outerBagCount){
    let sum = 0;
    if(!bagContents[outerBag]) return 0;
    for(let innerBag of Object.keys(bagContents[outerBag])){
        let innerBagCount = bagContents[outerBag][innerBag];
        sum+= outerBagCount*innerBagCount + countTarget2(innerBag, outerBagCount*innerBagCount);
    }
    return sum;
}

let sum = countTarget2(bagTarget, 1);
console.log(sum);