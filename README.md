# MySimpleLanguage
My simple jvm language compiler

## Язык программирования включает в себя базовые конструкции, такие как: 
### 1. Объявление целочисленной переменной
```
new variable_name = [other_variable_1|number_1 + ... other_variable_n|number_n]

new k = 50000
new m = 2 + 200
new z = m + 200
```
### 2. Переопределение значения переменной
```
variable_name = [other_variable_1|number_1 + ... other_variable_n|number_n]

k = 12
k = 12 + 15
k = m
``` 
### 3. Вывод значения на экран значения
```
print [other_variable_1|number_1 + ... other_variable_n|number_n]

print 1
print k
print 1 + k
print 1 + 1 + 1
``` 

### 4. Ветвление
```
if [other_var_1|number_1 + ... other_var_n|number_n] [<, =, >] [other_var_1|number_1 + ... other_var_n|number_n] {
    ...
} 

if 4 > 3 {
print 4
}
```

### 5. Цикл For 
```
for new var_name = [other_var_1|number_1 + ... other_var_n|number_n] : i = [other_var_1|number_1 + ... other_var_n|number_n] : i [<, =, >] [other_var_1|number_1 + ... other_var_n|number_n] {
    ...
}

for new i = 0 : i = 0 : i = i + 1 {
print 2222
}
```

### Цикл While
```
while(i = [other_var_1|number_1 + ... other_var_n|number_n]) {
    ...
}

while 4 > m {
 print m
 m = m + 1
}
```

## Правила оформления кода
- Каждая инструкция находится на новой строке
- Контент конструкций ветвления и циклов выжеляется фигурными скобочками 

## Целочисленные операции
- Сложение: 1+2

## Операции сравнения
- Меньше (1 < 2)
- Больше (1 > 2)
- Равно (1 = 2)