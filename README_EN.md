<div align="center">
  <img src="docs/images/xincomponent_logo.png" width="120" alt="XinComponent Logo" />

  <h1>XinComponent</h1>
  <p>A production-oriented Jetpack Compose design system and Android component library</p>

  <p><a href="README.md">简体中文</a></p>

  [![Kotlin](https://img.shields.io/badge/Kotlin-2.3.20-7F52FF?logo=kotlin&logoColor=white)](https://kotlinlang.org/)
  [![Compose](https://img.shields.io/badge/Compose-1.11.4-4285F4?logo=jetpackcompose&logoColor=white)](https://developer.android.com/compose)
  [![Material 3](https://img.shields.io/badge/Material%203-1.4.0-6750A4)](https://m3.material.io/)
  [![License](https://img.shields.io/badge/License-Apache%202.0-green.svg)](LICENSE)
</div>

XinComponent provides Compose applications with consistent design tokens, theming, and foundational interactive components. The current release prioritizes clear APIs, complete states, extensibility, and reliable publication. Reserved modules are explicitly identified instead of being advertised as finished features.

## Highlights

- Material 3 foundation with light/dark themes, Android 12+ dynamic color, and a custom brand color.
- Semantic color, text color, and shared 4dp spacing tokens exposed through `XinTheme`.
- Filled, outlined, and gradient buttons with semantic types, sizes, disabled, and loading states.
- Dialogs with confirm/cancel actions, asynchronous confirmation, dismissal policies, a maximum width, and slot-based customization.
- Android 7.0 (API 24) minimum; Kotlin 2.3.20, Compose 1.11.4, and Material 3 1.4.0.

## 📱 Preview

<table>
  <b> Component Preview
  <tr>
    <td><img src="docs/preview/overview.jpg" alt="组件概览"/></td>  
  </tr>
  <tr>
    <td><img src="docs/preview/appbar.jpg" alt="AppBar"/></td>
    <td><img src="docs/preview/applist.jpg" alt="AppList"/></td>
    <td><img src="docs/preview/text.jpg" alt="Text"/></td>
  </tr>
  <tr>
    <td><img src="docs/preview/button.jpg" alt="Button"/></td>
    <td><img src="docs/preview/card.jpg" alt="Card"/></td>
    <td><img src="docs/preview/dialog.jpg" alt="Dialog"/></td>
  </tr>
</table>

## Installation


### Jitpack

Add Repository in `settings.gradle.kts`

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
        }
    }
}
```

Use in `build.gradle.kts`
```kotlin
dependencies {
    implementation("com.github.Akiha678:XinComponent:v0.1.1")
}
```

## Quick start

Wrap the application root in `AppTheme`:

```kotlin
@Composable
fun MyApp() {
    AppTheme(dynamicColor = false) {
        // App content
    }
}
```

Supply a brand color or follow the system theme explicitly when needed:

```kotlin
AppTheme(
    darkTheme = isSystemInDarkTheme(),
    themeColor = Color(0xFF006C4C),
    dynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S,
) {
    AppContent()
}
```

## Components

### Button

```kotlin
AppButton(
    text = "Submit",
    type = ButtonType.SUCCESS,
    style = ButtonStyle.GRADIENT,
    size = ButtonSize.MEDIUM,
    loading = isSubmitting,
    enabled = formIsValid,
    onClick = ::submit,
)
```

Button APIs:

- `AppButton`: the primary action button, full-width by default.
- `AppButtonFixed`: a compact button sized by its content.
- `AppButtonBordered`: compatibility API for a customizable outlined button.
- `AppButtonCustomSize`: use when a layout requires an explicit width or height.
- `ButtonStyle`: `FILLED`, `OUTLINED`, and `GRADIENT`.
- `ButtonType`: `DEFAULT`, `SUCCESS`, `WARNING`, `DANGER`, `PURPLE`, and `LINK`.

When `loading` is true, the button disables interaction and keeps its label in the layout to prevent width shifts and duplicate submissions.

### Dialog

```kotlin
if (showDeleteDialog) {
    AppDialog(
        title = "Delete item?",
        content = "This action cannot be undone.",
        okText = "Delete",
        okColor = ColorDanger,
        confirmLoading = isDeleting,
        onOk = ::delete,
        onCancel = { showDeleteDialog = false },
        onDismiss = { showDeleteDialog = false },
    )
}
```

Callbacks do not dismiss the dialog automatically. The caller owns visibility, allowing an asynchronous operation to keep the dialog open and use `confirmLoading` to prevent duplicate actions. Complex screens can use the slot-based `AppDialog` overload.

### Loading

```kotlin
// Inline loading
AppLoading(
    text = "Loading...",
    size = LoadingSize.MEDIUM,
    orientation = LoadingOrientation.VERTICAL
)

// Modal loading dialog
AppLoadingDialog(
    visible = isLoading,
    text = "Submitting, please wait...",
    cancellable = false
)
```

### Empty

```kotlin
// Preset empty state (Data, Network, Error, Cart)
AppEmpty(
    type = EmptyType.NETWORK,
    onActionClick = { reloadData() }
)

// Custom empty state
AppEmpty(
    type = EmptyType.CUSTOM,
    title = "No notifications",
    subtitle = "New system messages will appear here",
    actionText = "Explore",
    onActionClick = { navigateToHome() }
)
```

### Badge

```kotlin
// Count badge (auto truncated to 99+)
AppBadge(count = unreadCount) {
    Icon(painter = painterResource(R.drawable.ic_notification), contentDescription = null)
}

// Dot badge
AppBadge(isDot = true) {
    Text("Messages")
}

// Standalone text badge
AppBadge(text = "HOT", containerColor = ColorDanger)
```

### TextField

```kotlin
AppTextField(
    value = username,
    onValueChange = { username = it },
    placeholder = "Enter phone or email",
    label = "Account",
    clearable = true,
    errorMessage = if (isError) "Invalid format" else null
)

// Password field with toggleable eye icon
AppTextField(
    value = password,
    onValueChange = { password = it },
    placeholder = "Enter password",
    isPassword = true
)
```

### Rate

```kotlin
// Rate with half-star support
AppRate(
    score = ratingScore,
    onScoreChange = { ratingScore = it },
    allowHalf = true
)

// Read-only star display
AppRate(
    score = 4.5f,
    onScoreChange = null
)
```

### Tag

```kotlin
Tag(text = "New Arrival", type = TagType.PRIMARY, style = TagStyle.LIGHT)
TagClosable(text = "Closable Tag", onClose = { removeTag() })
```

## Project structure

```text
XinComponent/
├── ui/       # Compose themes, tokens, and components
├── network/  # Reserved networking module
├── utils/    # Reserved utility module
├── docs/     # Documentation assets
└── gradle/   # Version catalog and Wrapper configuration
```

## Versioning and compatibility

The project follows semantic versioning:

- Patch releases fix defects without changing expected API behavior.
- Minor releases add backward-compatible capabilities; documented API adjustments can still occur during `0.x`.
- Major releases contain breaking changes after stabilization.

Applications should pin an explicit version and avoid dynamic versions. Read theme values through `XinTheme` and `MaterialTheme` rather than depending on implementation-level color constants.

## Contributing

Issues and pull requests are welcome. New components should include a clear public API, light/dark behavior, loading/disabled/error states where relevant, accessibility semantics, examples, tests, and release notes. Do not hard-code product copy inside components.

## License

XinComponent is released under the [Apache License 2.0](LICENSE).
