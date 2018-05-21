package tk.mybatis.mapper.code;

/**
 * 更新时间的时机
 * @date: 2018年1月4日 下午6:50:23
 * @author: jiuzhou.hu
 */
public enum GenerationTime {
    /**
     * 永不
     */
    NEVER,
    /**
     * 首次
     */
    INSERT,
    /**
     * 每次
     */
    ALWAYS
}
