package model;

public class PresetTask {
    private String name;
    private String memo;
    private int outin;
    private int categoryId;
    private String taskTime;

    // コンストラクタ
    public PresetTask(String name, String memo, int outin, int categoryId, String taskTime) {
        this.name = name;
        this.memo = memo;
        this.outin = outin;
        this.categoryId = categoryId;
        this.taskTime = taskTime;
    }

    // ゲッターとセッター
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getOutin() {
        return outin;
    }

    public void setOutin(int outin) {
        this.outin = outin;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }
}
