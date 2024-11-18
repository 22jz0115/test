package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Presets {
    private int id;
    private String presetName;
    private LocalDateTime createAt;
    private LocalDateTime updateDate;
    private List<PresetTask> tasks; // タスクのリスト（最大10個）

    public Presets(int id, String presetName, LocalDateTime createAt, LocalDateTime updateDate, List<PresetTask> tasks) {
        this.id = id;
        this.presetName = presetName;
        this.createAt = createAt;
        this.updateDate = updateDate;
        this.tasks = new ArrayList<>(); // 空のリストで初期化
    }
    // ゲッターとセッター
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPresetName() {
        return presetName;
    }

    public void setPresetName(String presetName) {
        this.presetName = presetName;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public List<PresetTask> getTasks() {
        return tasks;
    }

    // タスクを追加するメソッド（10個まで）
    public boolean addTask(PresetTask task) {
        if (tasks.size() < 10) {
            tasks.add(task);
            return true;
        }
        return false; // 10個を超える場合は追加しない
    }
}
