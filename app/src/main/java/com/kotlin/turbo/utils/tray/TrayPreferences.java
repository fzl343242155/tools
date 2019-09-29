/*
 * Copyright (C) 2015 grandcentrix GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kotlin.turbo.utils.tray;


import android.content.Context;

import androidx.annotation.NonNull;

import com.kotlin.turbo.utils.tray.core.AbstractTrayPreference;
import com.kotlin.turbo.utils.tray.core.TrayStorage;
import com.kotlin.turbo.utils.tray.provider.ContentProviderStorage;


public class TrayPreferences extends AbstractTrayPreference<ContentProviderStorage> {

    public TrayPreferences(@NonNull final Context context, @NonNull final String module,
                           final int version, final TrayStorage.Type type) {
        super(new ContentProviderStorage(context, module, type), version);
    }

    public TrayPreferences(@NonNull final Context context, @NonNull final String module,
                           final int version) {
        this(context, module, version, TrayStorage.Type.USER);
    }

    public void annexModule(final String oldStorageName, final TrayStorage.Type type) {
        super.annex(new ContentProviderStorage(getContext(), oldStorageName, type));
    }

    public void annexModule(final String oldStorageName) {
        annexModule(oldStorageName, TrayStorage.Type.UNDEFINED);
    }

    protected Context getContext() {
        return getStorage().getContext();
    }
}
