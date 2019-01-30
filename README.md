# EmotionEditor

EmotionEditor is a text editor based on [MediumEditor GWT](https://github.com/josepaiva94/medium-editor-gwt)
for highlighting emotions in text.

# Basic usage

```xml
    <repositories>
        <repository>
            <id>emotioneditor</id>
            <url>https://raw.github.com/josepaiva94/emotion-editor/emotion-editor-0.0.1</url>
            <snapshots>
                <enabled>true</enabled>
                <updatePolicy>always</updatePolicy>
            </snapshots>
        </repository>
    </repositories>
```

### Demo

Run it

```bash
mvn gwt:devmode
```

### Package

Package a GWT lib

```bash
mvn gwt:package-lib
```

### Deploy

Deploy GWT

```bash
mvn gwt:package-lib deploy
```

## Contributing

[Kill some bugs :)](https://github.com/josepaiva94/emotion-editor/issues?q=is%3Aopen+is%3Aissue+label%3Abug)

1. Fork it
2. Create your feature branch (`git checkout -b my-new-feature`)
3. Test your changes to the best of your ability.
4. Update the documentation to reflect your changes if they add or changes current functionality.
5. Commit your changes (`git commit -am 'Added some feature'`) **without files from the _target_ directory**.
6. Push to the branch (`git push origin my-new-feature`)
7. Create new Pull Request

## License

[MIT License](LICENSE)
