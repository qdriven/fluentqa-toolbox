package io.fluentqa.builtin.config;


import cn.hutool.setting.Setting;

public class QConfig {
  Setting setting;

  private QConfig() {
  }

  private final static String DEFAULT_SETTING_PATH = "config/app.setting";

  public static QConfig create() {
    return create(DEFAULT_SETTING_PATH);
  }

  /**
   * Create Setting by setting Paths
   * @param settingPath
   * @return
   */
  public static QConfig create(String settingPath) {
    QConfig config = new QConfig();
    config.setting = new Setting(settingPath);
    config.setting.autoLoad(true);
    return config;
  }

  public Setting getSetting() {
    return setting;
  }

  public Setting getConfigSet(String groupName) {
    return this.setting.getSetting(groupName);
  }
}
