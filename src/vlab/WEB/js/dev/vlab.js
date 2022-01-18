var Vlab = {

    div : null,

    setVariant : function(str){},
    setPreviosSolution: function(str){},
    setMode: function(str){},

    //Инициализация ВЛ
    init : function(){
        this.div = document.getElementById("jsLab");
        this.div.innerHTML = this.window;
        // document.getElementById("tool").innerHTML = this.tool;

        //получение варианта задания
        var ins = JSON.parse(document.getElementById("preGeneratedCode").value);
        this.div.innerHTML = "<div class=\"parent\"> <div class=\"disp\"> <canvas id=\"myCanvas\" width=\"500px\" height=\"500px\"></canvas> </div><div class=\"train\"> <table id=\"data_table\"> <tr> <th>Метрика 1</th> <th>Метрика 2</th> <th>Классификатор</th> </tr></table> </div><div class=\"ctrl\"> <b>Значение k: </b><div id=\"code_k\">?</div><table id=\"answer_table\"> <tr> <th>Метрика 1</th> <th>Метрика 2</th> <th>Классификатор</th> </tr><tr> <td id=\"ans_x\"></td><td id=\"ans_y\"></td><td> <select class=\"list1\" id=\"ans_cls\"> <option value=\"RED\" class=\"option1\">Красный</option> <option value=\"GREEN\" class=\"option2\">Зеленый</option> <option value=\"BLUE\" class=\"option3\">Синий</option> </select> </td></tr></table> </div></div>"
        this.window.code = ins;
    },

    getCondition: function(){},
    getResults: function(){
        return window.solution;
        },
    calculateHandler: function(text, code){},
}

window.onload = function() {
    Vlab.init();
};
