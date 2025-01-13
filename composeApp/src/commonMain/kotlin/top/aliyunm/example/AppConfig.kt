package top.aliyunm.example

object AppConfig {

    /**
     * 环境配置枚举
     */
    enum class EnvironmentConfig {
        Test,   // 测试环境
        Env     // 生产环境
    }

    /**
     * 主题配置枚举
     */
    enum class ThemeConfig {
        System, // 跟随系统
        Light,  // 白天主题
        Dark    // 暗黑主题
    }

    /**
     * 环境配置
     */
    val environment = EnvironmentConfig.Test

    /**
     * 主题配置
     */
    val theme = ThemeConfig.Light
}