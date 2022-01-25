// дефолтные параметры
var res = 500;
var dim = 40;
var lineSpacing = res / dim;
var coordStride = 5;
var coordSpacing = lineSpacing * coordStride;
var fontSize = 14;

// todo remove me in prod
// codeStr = "{\"dataPoints\":[{\"x\":3,\"y\":-9,\"cls\":\"GREEN\"},{\"x\":-4,\"y\":12,\"cls\":\"RED\"},{\"x\":19,\"y\":-13,\"cls\":\"RED\"},{\"x\":0,\"y\":-18,\"cls\":\"RED\"},{\"x\":12,\"y\":-7,\"cls\":\"BLUE\"},{\"x\":-8,\"y\":-2,\"cls\":\"GREEN\"},{\"x\":9,\"y\":-7,\"cls\":\"RED\"},{\"x\":-11,\"y\":-15,\"cls\":\"RED\"},{\"x\":12,\"y\":13,\"cls\":\"RED\"},{\"x\":-9,\"y\":-7,\"cls\":\"RED\"},{\"x\":-18,\"y\":-14,\"cls\":\"BLUE\"},{\"x\":12,\"y\":8,\"cls\":\"GREEN\"},{\"x\":11,\"y\":-14,\"cls\":\"RED\"}],\"pointToClassify\":{\"x\":4,\"y\":-11,\"cls\":null},\"k\":4}";
// this.window.code = JSON.parse(codeStr);

function refreshUI() {

    this.window.solution = {}
    this.window.solution.dataPoints = []

    var canvas = new fabric.Canvas('myCanvas');
    // create a rectangle object
    var rect = new fabric.Rect({
        left: 0,
        top: 0,
        fill: 'white',
        stroke: 'black',
        width: res,
        height: res,
        selectable: false
    });

    // "add" rectangle onto canvas
    canvas.add(rect);

    // Полоски по X
    for (var x = 0; x < res; x += lineSpacing) {
        var line = new fabric.Line([x, 0, x, res], {
            selectable: false
        });
        // Check if halfway on X
        if (x / lineSpacing == dim / 2) {
            line.stroke = 'black';
            line.strokeWidth = 2;
        } else {
            line.stroke = 'grey';
            line.strokeWidth = 0.5;
        }
        canvas.add(line);
    }

    // Полоски по Y
    for (var y = 0; y < res; y += lineSpacing) {
        var line = new fabric.Line([0, y, res, y], {
            selectable: false
        });
        // Check if halfway on Y
        if (y / lineSpacing == dim / 2) {
            line.stroke = 'black';
            line.strokeWidth = 2;
        } else {
            line.stroke = 'grey';
            line.strokeWidth = 0.5;
        }
        canvas.add(line);
    }

    // Коорд-ы по X
    for (var x = 0; x < res; x += coordSpacing) {
        var content = (x - (res / 2)) / coordSpacing * coordStride;
        if (content == 0) {
            continue;
        }
        canvas.add(new fabric.Text(String(content), {
            fontFamily: 'Times New Roman',
            left: x - fontSize / 2,
            fontSize: 14,
            stroke: 'red',
            strokeWidth: 1,
            top: res / 2,
            selectable: false
        }));
    }

    // Коорд-ы по Y
    for (var y = 0; y < res; y += coordSpacing) {
        var content = -(y - (res / 2)) / coordSpacing * coordStride;
        var y_coord = (content == 0) ? y + fontSize / 4 : y - fontSize / 2;
        canvas.add(new fabric.Text(String(content), {
            fontFamily: 'Delicious_500',
            left: res / 2 + fontSize / 2,
            fontSize: 14,
            stroke: 'red',
            top: y_coord,
            selectable: false
        }));
    }

    // Рисуем круги
    for (const dp of window.code.dataPoints) {

        var xc = dp.x * lineSpacing + res / 2;
        var yc = Math.abs((dp.y * lineSpacing + res / 2) - res);

        var circle = new fabric.Circle({
            radius: lineSpacing / 2,
            originX: 'center',
            originY: 'center',
            left: xc,
            top: yc,
            fill: 'black',
            selectable: false,
            dp: dp
        });
        canvas.add(circle);
        this.window.solution.dataPoints.push({'x': dp.x, 'y': dp.y, 'cls': null});

    }

    canvas.on('mouse:down', function(options) {
        if (options.target && options.target.type != 'rect' && options.target.dp) {
            var color = 'black';
            switch (options.target.fill){
                case 'black':
                    color = 'red';
                    break;
                case 'red':
                    color = 'green';
                    break;
                case 'green':
                    color = 'blue';
                    break;
                case 'blue':
                    color = 'black';
                    break;
            }
            options.target.set("fill", color);
            options.target.dp.cls = color;
            var dp = options.target.dp;
            for (const dps of window.solution.dataPoints) {
                if ((dps.x == dp.x) && (dps.y == dp.y)) {
                    const idx = window.solution.dataPoints.indexOf(dps);
                    window.solution.dataPoints.splice(idx, 1);
                }
            }
            window.solution.dataPoints.push({'x': dp.x, 'y': dp.y, 'cls': color});
            // canvas.renderAll();
        }
    });

    // Круг для классификации
    var xc = window.code.pointToClassify.x * lineSpacing + res / 2;
    var yc = Math.abs((window.code.pointToClassify.y * lineSpacing + res / 2) - res);

    var circle = new fabric.Circle({
        radius: lineSpacing / 2,
        originX: 'center',
        originY: 'center',
        left: xc,
        top: yc,
        fill: 'black',
        selectable: false
    });
    canvas.add(circle);

    canvas.renderAll();

    // Наполнение таблицы с трен. данными
    for (const td of window.code.dataPoints) {
        $("#data_table").append("<tr><td>" + td.x + "</td><td>" + td.y + "</td><td style='background: " + td.cls + "'></td></tr>")
    }

    // Наполнение блока контроля
    $('#code_k').text(window.code.k);
    $('#ans_x').text(window.code.pointToClassify.x);
    $('#ans_y').text(window.code.pointToClassify.y);

    $('#ans_cls').change(
        function (){
            var color = $('option:selected',this).css('background-color');
            var vl = $('option:selected', this).val();
            window.solution.cls = vl;
            $(this).css('background-color',color);
        }
    ).change();


};
