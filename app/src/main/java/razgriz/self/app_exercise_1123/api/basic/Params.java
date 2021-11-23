package razgriz.self.app_exercise_1123.api.basic;

import android.text.TextUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Params {
    private Map<String, Object> map = new HashMap();

    public Params() {
    }

    public void putRequired(String key, String value) {
        this.put(key, value, true);
    }

    public void putOption(String key, String value) {
        this.put(key, value, false);
    }

    private void put(String key, String value, boolean required) {
        if (TextUtils.isEmpty(value)) {
            if (required) {
                this.map.put(key, "");
            }
        } else {
            this.map.put(key, value);
        }

    }

    public void putRequired(String key, boolean value) {
        this.put(key, value, true, true);
    }

    public void putOption(String key, boolean value, boolean invalid) {
        this.put(key, value, false, invalid);
    }

    private void put(String key, boolean value, boolean required, boolean invalid) {
        if (required || value != invalid) {
            this.map.put(key, value);
        }

    }

    public void putRequired(String key, int value) {
        this.put(key, value, true, 0);
    }

    public void putOption(String key, int value) {
        this.put(key, value, false, 0);
    }

    public void putOption(String key, int value, int invalid) {
        this.put(key, value, false, invalid);
    }

    private void put(String key, int value, boolean required, int invalid) {
        if (required || value != invalid) {
            this.map.put(key, value);
        }

    }

    public void putRequired(String key, long value) {
        this.put(key, value, true, 0L);
    }

    public void putOption(String key, long value) {
        this.put(key, value, false, 0L);
    }

    public void putOption(String key, long value, long invalid) {
        this.put(key, value, false, invalid);
    }

    private void put(String key, long value, boolean required, long invalid) {
        if (required || value != invalid) {
            this.map.put(key, value);
        }

    }

    public void putRequired(String key, double value) {
        this.put(key, value, true, 0.0D);
    }

    public void putOption(String key, double value) {
        this.put(key, value, false, 0.0D);
    }

    public void putOption(String key, double value, double invalid) {
        this.put(key, value, false, invalid);
    }

    private void put(String key, double value, boolean required, double invalid) {
        if (required || value != invalid) {
            this.map.put(key, value);
        }

    }

    public void putRequired(String key, String[] values) {
        this.put(key, values, true);
    }

    public void putOption(String key, String[] values) {
        this.put(key, values, false);
    }

    private void put(String key, String[] values, boolean required) {
        if (values != null && values.length > 0) {
            StringBuilder sb = new StringBuilder();
            boolean first = true;
            String[] var6 = values;
            int var7 = values.length;

            for (int var8 = 0; var8 < var7; ++var8) {
                String s = var6[var8];
                if (first) {
                    first = false;
                } else {
                    sb.append(",");
                }

                sb.append(s);
            }

            this.map.put(key, sb.toString());
        } else if (required) {
            this.map.put(key, "");
        }

    }

    public void putRequired(String key, Collection<String> values) {
        this.put(key, values, true);
    }

    public void putOption(String key, Collection<String> values) {
        this.put(key, values, false);
    }

    private void put(String key, Collection<String> values, boolean required) {
        if (values != null && values.size() > 0) {
            StringBuilder sb = new StringBuilder();
            boolean first = true;

            String s;
            for (Iterator var6 = values.iterator(); var6.hasNext(); sb.append(s)) {
                s = (String) var6.next();
                if (first) {
                    first = false;
                } else {
                    sb.append(",");
                }
            }

            this.map.put(key, sb.toString());
        } else if (required) {
            this.map.put(key, "");
        }

    }

    public Map<String, Object> getMap() {
        return this.map;
    }
}
