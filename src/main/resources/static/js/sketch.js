let mobilenet;


const fileRead = () => {
  return new Promise((res, rej) => {
    console.log("test");
    let reader = new FileReader();
    reader.onload = () => {
      let dataURL = reader.result;
      $("#selected-image").attr("src", dataURL);
      res();
    };
    let file = $("#image_nameSrc").prop("files")[0];
    reader.readAsDataURL(file);
  });
};

$("#image_nameSrc").change(function () {
  fileRead().then(() => {
    let image = $("#selected-image").get(0);
    mobilenet.predict(image, gotResults);
  });
});


function modelReady() {
  console.log("Model is ready!!");
}

function gotResults(error, results) {
  if (error) {
    console.error(error);
  } else {
    console.log(results[0].label);
    const predictionResult=results[0].label;
    const firstPrediction = predictionResult.split(',')[0];
    $("#predictionResult").val(firstPrediction);

  }
}


function setup() {
  mobilenet = ml5.imageClassifier("MobileNet", modelReady);
}
