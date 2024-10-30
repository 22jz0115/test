function previewFiles(input) {
  var preview = document.getElementById('preview');
  preview.innerHTML = '';  // プレビューをクリアする
  var files = input.files;

  if (files) {
    for (var i = 0; i < files.length; i++) {
      var file = files[i];
      var fileReader = new FileReader();

      fileReader.onload = (function(e) {
        var img = document.createElement('img');
        img.src = e.target.result;
        img.style.width = "200px";  // プレビュー画像のサイズ設定
        img.style.margin = "10px";  // 少し余白を追加
        preview.appendChild(img);
      });

      fileReader.readAsDataURL(file);
    }
  }
}