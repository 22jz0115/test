let isImageDeleted = false; // 画像が削除されたかのフラグ

function previewFiles(input) {
    const preview = document.getElementById('preview');
    if (preview) {
        preview.innerHTML = ''; // プレビューをリセット

        const files = input.files;
        
        console.log(files);
        for (let i = 0; i < files.length; i++) {
            const file = files[i];
            const reader = new FileReader();
            
            reader.onload = function(e) {
                const img = document.createElement('img');
                img.src = e.target.result;
                img.style.width = '150px';
                img.style.margin = '5px';
                preview.appendChild(img);
            };
            reader.readAsDataURL(file);
        }
    }
}


document.getElementById('deleteImageButton')?.addEventListener('click', () => {
    const currentPhoto = document.getElementById('currentPhoto');
    const previewImage = document.getElementById('previewImage');
    const fileInput = document.getElementById('file');
    const preview = document.getElementById('preview');
    console.log(preview);

    if (previewImage) {
        previewImage.remove(); // プレビュー画像削除
        currentPhoto.innerHTML = '<p>現在の写真は削除されました。</p>';
        fileInput.value = ''; // ファイル選択状態をリセット
        fileInput.removeAttribute('disabled'); // 新しい写真選択を可能にする
        preview.innerHTML = ''; // プレビューをクリア
        isImageDeleted = true; // 画像削除フラグをセット

        // 削除フラグをフォームに追加
        document.getElementById('deleteImage').value = 'true'; // deleteImage を 'true' に設定
    }
});

document.getElementById('file').addEventListener('change', function() {
    // 画像削除後に再度写真を選択した場合、プレビューを更新
    if (isImageDeleted) {
        isImageDeleted = false; // フラグをリセット
        document.getElementById('deleteImage').value = 'false'; // deleteImage を 'false' に設定
        const preview = document.getElementById('preview');
        if (preview) {
            preview.innerHTML = ''; // プレビューをリセット
        }
    }

    previewFiles(this);
});

// フォーム送信時に画像削除フラグが送信されるようにする
document.getElementById('lifeChangeForm').addEventListener('submit', function(e) {
    if (isImageDeleted) {
        document.getElementById('deleteImage').value = 'true'; // 画像削除フラグを送信
    }
});
