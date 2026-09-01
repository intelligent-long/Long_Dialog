# Long Dialog
Android Dialog 库

# 支持类型
1. 消息弹窗
2. 确认弹窗
3. 选项弹窗
4. 进行中弹窗
5. 进度条进行中弹窗
6. 输入弹窗
7. 列表弹窗

# 使用示例

## 创建 Dialog
```java
         binding.messageDialog.setOnClickListener(view -> {
            LongDialog.newMessageDialog(this)
                    .message("这是一个消息弹窗。")
                    .okButtonText("完成")
                    .cancelable(false)
                    .create()
                    .show();
        });

        binding.confirmDialog.setOnClickListener(view -> {
            LongDialog.newConfirmDialog(this)
                    .icon(R.drawable.help_24px)
                    .message("确定要执行此操作吗？")
                    .okDialogButtonYier((dialogInterface, i) -> {
                        Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show();
                    })
                    .create()
                    .show();
        });

        binding.optionDialog.setOnClickListener(view -> {
            LongDialog.newOptionDialog(this)
                    .title("选项弹窗")
                    .icon(R.drawable.help_24px)
                    .message("这是一个选项弹窗。")
                    .positiveButtonText("好")
                    .neutralButtonText("选择")
                    .negativeButtonText("取消")
                    .positiveDialogButtonYier((dialogInterface, i) -> {
                        Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
                    })
                    .neutralButtonYier(view1 -> {
                        Toast.makeText(this, "neutral", Toast.LENGTH_SHORT).show();
                    })
                    .create()
                    .show();
        });

        binding.progressingDialog.setOnClickListener(view -> {
            LongDialog.newProgressingDialog(this)
                    .create()
                    .show();
        });

        binding.progressDialog.setOnClickListener(view -> {
            ProgressDialog progressOperatingDialog =LongDialog.newProgressDialog(this)
                    .create()
                    .show();
            new Timer().schedule(new TimerTask() {
                int progress;
                @Override
                public void run() {
                    if(progress > 10000){
                        cancel();
                    }
                    if(progressOperatingDialog.getProgress() < 3000){
                        progressOperatingDialog.updateText("开始...");
                    }else if(progressOperatingDialog.getProgress() < 5000){
                        progressOperatingDialog.updateText("进行中...");
                    }else if(progressOperatingDialog.getProgress() < 7000){
                        progressOperatingDialog.updateText("等待中...");
                    }else{
                        progressOperatingDialog.updateText("即将结束");
                    }
                    progress += 500;
                    progressOperatingDialog.updateProgress(progress, 10000);
                }
            }, 0, 500);
        });

        binding.inputDialog.setOnClickListener(view -> {
            InputDialog inputDialog = LongDialog.newInputDialog(this)
                    .icon(R.drawable.info_24px)
                    .title("输入弹窗")
                    .message("这是一个输入弹窗。")
                    .cancelButtonText("取消")
                    .okButtonText("完成")
                    .hint("请输入内容");
            inputDialog.okButtonYier(view1 -> {
                        String inputText = inputDialog.getText();
                        if (inputText.isEmpty()) {
                            inputDialog.setError("内容不能为空");
                        } else {
                            Toast.makeText(this, "输入了: " + inputText, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .create()
                    .show();
        });

        binding.listDialog1.setOnClickListener(view -> {
            LongDialog.newListDialog(this)
                    .message("这是一个列表弹窗。")
                    .items(new CharSequence[]{"选项1", "选项2"}, (dialogInterface, i) -> {
                        Toast.makeText(this, "点击了: " + i, Toast.LENGTH_SHORT).show();
                    })
                    .create()
                    .show();
        });
```

## 全局资源配置
```java
LongDialog.material3Theme();
LongDialog.material2ThemeDayNight();
```
```java
LongDialog.okButtonText = R.string.ok;
LongDialog.confirmText = R.string.confirm_text;
LongDialog.theme = R.style.Theme_LongDialog;
```
