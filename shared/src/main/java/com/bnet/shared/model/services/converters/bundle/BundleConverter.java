package com.bnet.shared.model.services.converters.bundle;

import android.os.Bundle;

import com.bnet.shared.model.backend.Providable;
import com.bnet.shared.model.services.converters.Converter;

public interface BundleConverter<T extends Providable> extends Converter<Bundle, T> {
    Bundle convert(T item);
    T convert(Bundle bundle);
}
