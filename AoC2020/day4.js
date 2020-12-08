let input = require('./day4_input');

let requiredFields = ['byr', 'iyr', 'eyr', 'hgt', 'hcl', 'ecl', 'pid'];
let optionalFields = ['cid'];

function getPassportObjs(passports) {
    let passportsObjs = [];
    for (let passport of passports.split('\n\n')) {
        let fields = passport.split('\n').map(p => p.split(' ')).reduce((prev, curr) => [...prev, ...curr], []);
        let obj = fields.reduce((prev, curr) => {
            let [field, val] = curr.split(':');
            prev[field] = val;
            return prev;

        }, {});
        passportsObjs.push(obj);
    }
    return passportsObjs;
}


let partA = (() => {
    let passportsObjs = getPassportObjs(input);
    let validPassports = passportsObjs.reduce((prev, curr) => hasRequiredFields(curr) ? prev + 1 : prev, 0)
    console.log('A', validPassports);
})()

// byr (Birth Year) - four digits; at least 1920 and at most 2002.
// iyr (Issue Year) - four digits; at least 2010 and at most 2020.
// eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
// hgt (Height) - a number followed by either cm or in:
//     If cm, the number must be at least 150 and at most 193.
//     If in, the number must be at least 59 and at most 76.
// hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
// ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
// pid (Passport ID) - a nine-digit number, including leading zeroes.
// cid (Country ID) - ignored, missing or not.
let fieldValidation = {
    byr: validNumber(1920, 2002),
    iyr: validNumber(2010, 2020),
    eyr: validNumber(2020, 2030),
    hgt: (val) => validNumber(150, 193, 'cm')(val) || validNumber(59, 76, 'in')(val),
    hcl: (val) => /^#[0-9a-f]{6}$/.test(val),
    ecl: (val) => /^amb|blu|brn|gry|grn|hzl|oth$/.test(val),
    pid: (val) => /^[0-9]{9}$/.test(val),
    cid: () => true
}

let partB = (() => {
    let passportsObjs = getPassportObjs(input);
    let validPassports = passportsObjs.reduce((prev, curr) => hasValidFields(curr) ? prev + 1 : prev, 0)
    console.log('B', validPassports);
})();

function hasRequiredFields(passportObj) {
    for (let requiredField of requiredFields) {
        if (!passportObj[requiredField]) return false;
    }
    return true;
}

function hasValidFields(passportObj) {
    for (let requiredField of requiredFields) {
        let val = passportObj[requiredField];
        if (!passportObj[requiredField]) return false;
        if (!fieldValidation[requiredField](val)) return false;
    }
    return true;
}

function validNumber(min, max, suffix = null) {
    return (val) => {
        if (suffix && String(val).slice(suffix.length * -1) !== suffix) return false;
        let newVal = suffix ? Number(val.slice(0, suffix.length * -1)) : val;
        return newVal >= min && newVal <= max;
    }
}